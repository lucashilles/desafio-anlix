package br.com.lucashilles.domains.reading;

import io.vertx.core.json.JsonObject;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/reading")
@Produces(MediaType.APPLICATION_JSON)
public class ReadingController {

    @Inject
    ReadingService readingService;

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
}
