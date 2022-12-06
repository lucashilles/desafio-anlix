package br.com.lucashilles.domains.reading.readingtype;


import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static br.com.lucashilles.domains.message.GenericMessage.*;

@Path("/reading-type")
@Produces(MediaType.APPLICATION_JSON)
public class ReadingTypeController {

    @Inject
    ReadingTypeService readingTypeService;

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteReadingType(@PathParam long id) {
        if (id < 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ID_MUST_BE_POSITIVE).build();
        }
        readingTypeService.delete(id);

        return Response.ok().build();
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
    public Response getById(@PathParam long id) {
        if (id < 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ID_MUST_BE_POSITIVE).build();
        }

        ReadingType readingType = ReadingType.findById(id);
        if (readingType == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(ENTITY_NOT_FOUND).build();
        }

        return Response.ok(readingType).build();
    }

    @GET
    @Path("/all")
    public List<ReadingType> getAll() {
        return ReadingType.findAll().list();
    }
}
