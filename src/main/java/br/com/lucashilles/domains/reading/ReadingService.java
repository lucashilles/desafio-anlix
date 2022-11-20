package br.com.lucashilles.domains.reading;

import org.hibernate.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ReadingService {

    @Inject
    Session session;

    public Reading create(Reading reading) {
        Reading.persist(reading);
        return reading;
    }

    public void delete(long id) {
        Reading reading = Reading.findById(id);
        if (reading != null) {
            reading.delete();
        }
    }

    public Reading update(Reading reading) {
        reading.persist();
        return reading;
    }

    public List<Reading> getWithParams(Map<String, Object> parameters) {
        String whereQuery = "";

        if (parameters.get("patient") != null) {
            whereQuery += "patient_id = :patient AND ";
        }

        if (parameters.get("reading_type") != null) {
            whereQuery += "reading_type_id = :reading_type AND ";
        }

        if (parameters.get("from_date") != null && parameters.get("to_date") != null) {
            whereQuery += "BETWEEN DATE(:from_date) AND DATE(:to_date) AND ";
        } else if (parameters.get("from_date") != null) {
            whereQuery += "DATE(date) >= DATE(:from_date) AND ";
        } else if (parameters.get("to_date") != null) {
            whereQuery += "DATE(date) <= DATE(:to_date) AND ";
        } else if (parameters.get("date") != null) {
            whereQuery += "DATE(date) = DATE(:date) AND ";
        }

        whereQuery = whereQuery.substring(0, whereQuery.length() - 5);

        return Reading.find(whereQuery, parameters).list();
    }

    public List<Reading> getAllLatest() {
        String query = "SELECT r.* " +
                "FROM sys_reading r " +
                "JOIN ( " +
                "    SELECT MAX(date) AS date, reading_type_id, patient_id " +
                "    FROM sys_reading " +
                "    GROUP BY patient_id, reading_type_id " +
                ") f " +
                "ON r.date = f.date " +
                "AND r.reading_type_id = f.reading_type_id " +
                "AND r.patient_id = f.patient_id; ";

        return session.createNativeQuery(query, Reading.class).list();
    }

    public List<Reading> getAllLatestByPatient(Long patientId) {
        String query = "SELECT r.*" +
                " FROM sys_reading r" +
                " JOIN (" +
                "     SELECT MAX(date) AS date, reading_type_id, patient_id" +
                "     FROM sys_reading AS f" +
                "     WHERE patient_id = :patientId" +
                "     GROUP BY reading_type_id, patient_id" +
                " ) f" +
                " ON r.date = f.date" +
                " AND r.reading_type_id = f.reading_type_id;";

        return session.createNativeQuery(query, Reading.class).setParameter("patientId", patientId).list();
    }

    public List<Reading> getAllLatestByType(Long readingTypeId) {
        String query = "SELECT r.*" +
                " FROM sys_reading r" +
                " JOIN (" +
                "     SELECT MAX(date) AS date, reading_type_id, patient_id" +
                "     FROM sys_reading" +
                "     WHERE reading_type_id = :readingTypeId" +
                "     GROUP BY patient_id, reading_type_id" +
                " ) f" +
                " ON r.date = f.date" +
                " AND r.reading_type_id = f.reading_type_id" +
                " AND r.patient_id = f.patient_id;";

        return session.createNativeQuery(query, Reading.class).setParameter("readingTypeId", readingTypeId).list();
    }

    public List<Reading> getLatestByPatientAndType(Long patientId, Long readingTypeId) {
        String query = "SELECT r.*" +
                " FROM sys_reading r" +
                " JOIN (" +
                "     SELECT MAX(date) AS date, reading_type_id, patient_id" +
                "     FROM sys_reading" +
                "     WHERE patient_id = :patientI "+
                "        AND reading_type_id = :readingTypeId" +
                "     GROUP BY patient_id, reading_type_id" +
                " ) f" +
                " ON r.date = f.date" +
                " AND r.reading_type_id = f.reading_type_id" +
                " AND r.patient_id = f.patient_id;";

        return session.createNativeQuery(query, Reading.class)
                .setParameter("patientId", patientId)
                .setParameter("readingTypeId", readingTypeId)
                .list();
    }

    public List<Reading> getPatientStatus(Long patientId) {
        String query = "SELECT" +
                "     r.*  " +
                " FROM public.sys_reading AS r" +
                " JOIN (" +
                "     SELECT MAX(gp.date) AS date, gp.reading_type_id" +
                "     FROM public.sys_reading AS gp" +
                "     WHERE gp.patient_id = :id" +
                "     GROUP BY gp.reading_type_id" +
                " ) AS gp ON r.date = gp.date AND r.reading_type_id = gp.reading_type_id";

        return session.createNativeQuery(query, Reading.class).setParameter("id", patientId).list();
    }
}
