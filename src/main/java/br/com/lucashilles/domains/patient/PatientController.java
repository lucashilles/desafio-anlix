package br.com.lucashilles.domains.patient;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/patient")
public class PatientController {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Patient getById(@PathParam long id) {
        return Patient.findById(id);
    }

    @GET
    @Path("/search/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> getLastReadings(@PathParam String name) {
        return Patient.list("LOWER(name) LIKE ?1", "%" + name.toLowerCase() + "%");
    }

}
