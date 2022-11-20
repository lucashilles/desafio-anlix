package br.com.lucashilles.domains.patient;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/patient")
@Produces(MediaType.APPLICATION_JSON)
public class PatientController {

    @Inject
    PatientService patientService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Patient createPatient(Patient newPatient) {
        return patientService.create(newPatient);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Patient updatePatient(Patient patient) {
        return patientService.update(patient);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteById(@PathParam long id) {
        patientService.delete(id);
    }

    @GET
    @Path("/{id}")
    public Patient getById(@PathParam long id) {
        return Patient.findById(id);
    }

    @GET
    @Path("/find")
    public List<Patient> findPatient(@QueryParam String name) {
        return Patient.list("LOWER(name) LIKE ?1", "%" + name.toLowerCase() + "%");
    }

    @GET
    @Path("/{id}/status")
    public List<ReadingProjection> getPatientStatus(@Parameter(
            description = "Number of records to be returned.",
            required = true,
            schema = @Schema(type = SchemaType.INTEGER)) @PathParam long id) {
        List<Reading> patientStatus = patientService.getPatientStatus(id);
        return patientStatus.stream().map(ReadingProjection::new).collect(Collectors.toList());
    }
}
