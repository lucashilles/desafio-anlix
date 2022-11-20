package br.com.lucashilles.domains.patient;

import br.com.lucashilles.domains.reading.Reading;
import br.com.lucashilles.domains.reading.ReadingProjection;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

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
    public Response deleteById(@PathParam long id) {
        patientService.delete(id);
        return Response.ok("Patient deleted.").build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam long id) {
        Patient patient = Patient.findById(id);
        return Response.ok(patient).build();
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
