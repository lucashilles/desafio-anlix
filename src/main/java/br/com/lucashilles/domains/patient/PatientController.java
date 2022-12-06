package br.com.lucashilles.domains.patient;

import br.com.lucashilles.domains.reading.Reading;
import br.com.lucashilles.domains.reading.ReadingProjection;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.lucashilles.domains.message.GenericMessage.ENTITY_NOT_FOUND;
import static br.com.lucashilles.domains.message.GenericMessage.ID_MUST_BE_POSITIVE;

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
        if (id < 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ID_MUST_BE_POSITIVE).build();
        }

        patientService.delete(id);
        return Response.ok("Patient deleted.").build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam long id) {
        if (id < 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ID_MUST_BE_POSITIVE).build();
        }

        Patient patient = Patient.findById(id);

        if (patient == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(ENTITY_NOT_FOUND).build();
        }

        return Response.ok(patient).build();
    }

    @GET
    @Path("/find")
    public Response findPatient(@QueryParam String name) {
        if (name.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Patient> patientList = Patient.list("LOWER(name) LIKE ?1", "%" + name.toLowerCase() + "%");

        if (patientList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity(ENTITY_NOT_FOUND).build();
        }

        return Response.ok(patientList).build();
    }

    @GET
    @Path("/{id}/status")
    public List<ReadingProjection> getPatientStatus(@PathParam long id) {
        List<Reading> patientStatus = patientService.getPatientStatus(id);
        return patientStatus.stream().map(ReadingProjection::new).collect(Collectors.toList());
    }
}
