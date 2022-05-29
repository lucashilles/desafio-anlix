package br.com.lucashilles.domains.reading.readingtype;


import io.vertx.core.json.JsonObject;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/reading_type")
public class ReadingTypeController {

    @Inject
    ReadingTypeService readingTypeService;

    @Transactional
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public ReadingType createReadingType(JsonObject body) {
        return readingTypeService.createReadingType(body.getString("name"));
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public ReadingType getByName(JsonObject body) {
        return ReadingType.find("name", body.getString("name")).firstResult();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ReadingType getById(@PathParam long id) {
        return ReadingType.findById(id);
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReadingType> getAll() {
        return ReadingType.findAll().list();
    }
}
