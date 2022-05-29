package br.com.lucashilles.domains.common;


import br.com.lucashilles.domains.patient.Patient;
import br.com.lucashilles.domains.reading.Reading;
import br.com.lucashilles.domains.reading.readingtype.ReadingType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/common")
public class CommonController {

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "Pong!";
    }

    @GET
    @Path("/totals")
    @Produces(MediaType.APPLICATION_JSON)
    public ObjectNode totals() {
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

        return totalsJson;
    }
}
