package br.com.lucashilles.domains.reading;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.lucashilles.domains.message.GenericMessage.*;

@Path("/reading")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Reading",
        description = "Endpoints of the Reading entity."
)
public class ReadingController {

    @Inject
    ReadingService readingService;

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteReading(@PathParam long id) {
        if (id < 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ID_MUST_BE_POSITIVE).build();
        }

        readingService.delete(id);
        return Response.ok().build();
    }

    @GET
    public Response getReadings (
            @QueryParam Long patient,
            @QueryParam("reading-type") Long readingType,
            @QueryParam("from-date")  Date fromDate,
            @QueryParam("to-date")  Date toDate,
            @QueryParam Date date
    ) throws Exception {
        if (date != null && (fromDate != null || toDate != null)) {
            throw new Exception(USE_DATE_ALONE);
        }
        HashMap<String, Object> parameters = new HashMap<>();

        if (patient != null) {
            parameters.put("patient", patient);
        }

        if (readingType != null) {
            parameters.put("reading_type", readingType);
        }

        if (fromDate != null) {
            parameters.put("from_date", fromDate);
        }

        if (toDate != null) {
            parameters.put("to_date", toDate);
        }

        if (date != null) {
            parameters.put("date", date);
        }

        boolean haveDateRange = fromDate != null && toDate != null;
        if (haveDateRange && toDate.before(fromDate)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(INVALID_DATE_RANGE).build();
        }

        List<Reading> readings = readingService.getWithParams(parameters);

        if (readings.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity(ENTITY_NOT_FOUND).build();
        }

        List<ReadingProjection> projectedList = readings.stream().map(ReadingProjection::new).collect(Collectors.toList());
        return Response.ok(projectedList).build();
    }

    @POST
    @Transactional
    public ReadingProjection createReading(ReadingProjection newReading) {
        Reading reading = readingService.create(newReading.toEntity());
        return new ReadingProjection(reading);
    }

    @PUT
    @Transactional
    public ReadingProjection updateReading(Reading modifiedReading) {
        Reading reading = readingService.update(modifiedReading);
        return new ReadingProjection(reading);
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam long id) {
        if (id < 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ID_MUST_BE_POSITIVE).build();
        }

        Reading reading = Reading.findById(id);

        if (reading == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(ENTITY_NOT_FOUND).build();
        }

        return Response.ok(new ReadingProjection(reading)).build();
    }

    @GET
    @Path("/latest")
    public Response getLatest(@QueryParam Long patient, @QueryParam("reading-type") Long readingType) {
        List<Reading> readings = null;

        if (patient == null && readingType == null) {
            readings = readingService.getAllLatest();
        } else if (readingType == null) {
            readings = readingService.getAllLatestByPatient(patient);
        } else if (patient == null) {
            readings = readingService.getAllLatestByType(readingType);
        }

        if (readings == null || readings.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity(ENTITY_NOT_FOUND).build();
        } else {
            List<ReadingProjection> projectedReadings = readings.stream().map(ReadingProjection::new).collect(Collectors.toList());
            Response.ok(projectedReadings);
        }

        List<Reading> latestByPatientAndType = readingService.getLatestByPatientAndType(patient, readingType);
        if (latestByPatientAndType.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity(ENTITY_NOT_FOUND).build();
        }

        List<ReadingProjection> projections = latestByPatientAndType.stream().map(ReadingProjection::new).collect(Collectors.toList());
        return Response.ok(projections).build();
    }
}
