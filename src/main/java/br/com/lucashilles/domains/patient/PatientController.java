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

//    @GET
//    @Path("/{id}/status")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<ReadingDto> getLastReadings(@PathParam long id) {
//        String query = "SELECT  " +
//                "    r.*  " +
//                "FROM public.sys_reading AS r  " +
//                "JOIN (  " +
//                "    SELECT MAX(gp.date) AS date, gp.reading_type_id  " +
//                "    FROM public.sys_reading AS gp  " +
//                "    WHERE gp.patient_id = :id  " +
//                "    GROUP BY gp.reading_type_id  " +
//                ") AS gp ON r.date = gp.date AND r.reading_type_id = gp.reading_type_id";
//
//        return session.createNativeQuery(query, Reading.class).setParameter("id", id).list();
//    }

//    @GET
//    @Path("/{id}/last-readings")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Reading> getLastReadings(@PathParam long id) {
//        String query = "SELECT  " +
//                "    r.*  " +
//                "FROM public.sys_reading AS r  " +
//                "JOIN (  " +
//                "    SELECT MAX(gp.date) AS date, gp.reading_type_id  " +
//                "    FROM public.sys_reading AS gp  " +
//                "    WHERE gp.patient_id = :id  " +
//                "    GROUP BY gp.reading_type_id  " +
//                ") AS gp ON r.date = gp.date AND r.reading_type_id = gp.reading_type_id";
//
//        return session.createNativeQuery(query, Reading.class).setParameter("id", id).list();
//    }


//    SELECT *
//    FROM sys_reading r
//    JOIN (
//      SELECT max(date) AS date, reading_type_id, patient_id
//      FROM sys_reading
//      GROUP BY patient_id, reading_type_id
//    ) f
//    ON r.date = f.date
//    AND r.reading_type_id = f.reading_type_id
//    AND r.patient_id = f.patient_id
}
