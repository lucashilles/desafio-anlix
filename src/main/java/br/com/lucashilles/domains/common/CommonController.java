package br.com.lucashilles.domains.common;


import br.com.lucashilles.domains.patient.Patient;
import br.com.lucashilles.domains.reading.Reading;
import br.com.lucashilles.domains.reading.readingtype.ReadingType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/common")
public class CommonController {

    @Inject
    CommonService commonService;

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "Pong!";
    }

    @GET
    @Path("/populate")
    @Operation(operationId = "populate",
            summary = "populate the database",
            description = "This operation imports the data provided with this challenge"
    )
    @Produces(MediaType.TEXT_PLAIN)
    public Response populate() {
        try {
            commonService.populate();
        } catch (Exception exception) {
            return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
        }
        return Response.ok().build();
    }

    @GET
    @Path("/totals")
    @Produces(MediaType.APPLICATION_JSON)
    public Response totals() {
        long patientCount = Patient.count();
        long readingTypeCount = ReadingType.count();
        long cardiacReadingCount = Reading.count("reading_type_id", 1);
        long pulmonaryReadingCount = Reading.count("reading_type_id", 2);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode totalsJson = mapper.createObjectNode();
        ObjectNode readings = mapper.createObjectNode();

        totalsJson.put("patients", patientCount);

        readings.put("type", readingTypeCount);
        readings.put("cardiac", cardiacReadingCount);
        readings.put("pulmonary", pulmonaryReadingCount);

        totalsJson.set("readings", readings);

        return Response.ok(totalsJson).build();
    }
}
