package br.com.lucashilles.domains.patient;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/patient")
public class PatientController {

    @Inject
    PatientService patientService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Patient createPatient(Patient newPatient) {
        return patientService.createPatient(newPatient);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Patient updatePatient(Patient patient) {
        return patientService.updatePatient(patient);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteById(@PathParam long id) {
        patientService.deletePatient(id);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Patient getById(@PathParam long id) {
        return Patient.findById(id);
    }

    @GET
    @Path("/find/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> findPatient(@PathParam String name) {
        return Patient.list("LOWER(name) LIKE ?1", "%" + name.toLowerCase() + "%");
    }

}
