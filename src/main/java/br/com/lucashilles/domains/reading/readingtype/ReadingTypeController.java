package br.com.lucashilles.domains.reading.readingtype;


import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/reading-type")
@Produces(MediaType.APPLICATION_JSON)
public class ReadingTypeController {

    @Inject
    ReadingTypeService readingTypeService;

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteReadingType(@PathParam long id) {
        readingTypeService.delete(id);
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public ReadingType createReadingType(ReadingType newReadingType) {
        return readingTypeService.create(newReadingType);
    }

    @POST
    @Path("/")
    @Consumes(MediaType.TEXT_PLAIN)
    @Transactional
    public ReadingType createReadingTypeByName(String name) {
        return readingTypeService.create(name);
    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public ReadingType updateReadingType(ReadingType readingType) {
        return readingTypeService.update(readingType);
    }

    @GET
    @Path("/{id}")
    public ReadingType getById(@PathParam long id) {
        return ReadingType.findById(id);
    }

    @GET
    @Path("/all")
    public List<ReadingType> getAll() {
        return ReadingType.findAll().list();
    }
}
