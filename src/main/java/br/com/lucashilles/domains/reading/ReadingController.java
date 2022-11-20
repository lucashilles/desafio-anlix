package br.com.lucashilles.domains.reading;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Path("/reading")
@Produces(MediaType.APPLICATION_JSON)
public class ReadingController {

    @Inject
    ReadingService readingService;

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteReading(@PathParam long id) {
        readingService.delete(id);
    }

    @GET
    public List<ReadingProjection> getReadings (
            @QueryParam Long patient,
            @QueryParam("reading-type") Long readingType,
            @QueryParam("from-date")  Date fromDate,
            @QueryParam("to-date")  Date toDate,
            @QueryParam Date date
    ) throws Exception {
        if (date != null && (fromDate != null || toDate != null)) {
            throw new Exception("Date can only be use alone");
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

        List<Reading> readings = readingService.getWithParams(parameters);
        return readings.stream().map(ReadingProjection::new).collect(Collectors.toList());
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
    public ReadingProjection getById(@PathParam long id) {
        Reading reading = Reading.findById(id);
        return new ReadingProjection(reading);
    }

    @GET
    @Path("/latest")
    public List<ReadingProjection> getLatest(@QueryParam Long patient, @QueryParam("reading-type") Long readingType) {
        if (patient == null && readingType == null) {
            List<Reading> allLatest = readingService.getAllLatest();
            return allLatest.stream().map(ReadingProjection::new).collect(Collectors.toList());
        } if (readingType == null) {
            List<Reading> allLatestByPatient = readingService.getAllLatestByPatient(patient);
            return allLatestByPatient.stream().map(ReadingProjection::new).collect(Collectors.toList());
        } if (patient == null) {
            List<Reading> allLatestByType = readingService.getAllLatestByType(readingType);
            return allLatestByType.stream().map(ReadingProjection::new).collect(Collectors.toList());
        }

        List<Reading> latestByPatientAndType = readingService.getLatestByPatientAndType(patient, readingType);
        return latestByPatientAndType.stream().map(ReadingProjection::new).collect(Collectors.toList());
    }
}
