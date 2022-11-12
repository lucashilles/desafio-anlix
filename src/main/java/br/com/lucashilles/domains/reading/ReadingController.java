package br.com.lucashilles.domains.reading;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/reading")
@Produces(MediaType.APPLICATION_JSON)
public class ReadingController {

    @Inject
    ReadingService readingService;

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteReading(@PathParam long id) {
        readingService.deleteReading(id);
    }

    @GET
    public List<ReadingDto> getReadings (
            @QueryParam Long patient,
            @QueryParam("reading-type") Long readingType,
            @QueryParam String from,
            @QueryParam String to,
            @QueryParam String date
    ) throws Exception {
        if (date != null && (from != null || to != null)) {
            throw new Exception("Date can only be use alone");
        }
        return null;
    }

    @POST
    @Transactional
    public Reading createReading(Reading reading) {
        return readingService.createReading(reading);
    }

    @PUT
    @Transactional
    public Reading updateReading(Reading reading) {
        return readingService.updateReading(reading);
    }

    @GET
    @Path("/{id}")
    public Reading getById(@PathParam long id) {
        return Reading.findById(id);
    }

    @GET
    @Path("/all")
    public List<ReadingDto> getAllByDate(@QueryParam String date) {
        return readingService.getAllByDate(date);
    }

    @GET
    @Path("/latest")
    public List<ReadingDto> getLatest(@QueryParam Long patient, @QueryParam("reading-type") Long readingType) {
        if (patient == null && readingType == null) {
            return readingService.getAllLatest();
        } if (readingType == null) {
            return readingService.getAllLatestByPatient(patient);
        } if (patient == null) {
            return readingService.getAllLatestByType(readingType);
        }

        return readingService.getLatestByPatientAndType(patient, readingType);
    }
}
