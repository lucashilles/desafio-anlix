package br.com.lucashilles.domains.reading;

import org.hibernate.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

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

    public List<ReadingProjection> getAllByDate(String date) {
        String query = "DATE(date) = DATE(?1)";
        return Reading.find(query, date).project(ReadingProjection.class).list();
    }

    public List<ReadingProjection> getAllLatest() {
        String query = "SELECT r.*" +
                "FROM sys_reading r" +
                "JOIN (" +
                "    SELECT MAX(date) AS date, reading_type_id, patient_id" +
                "    FROM sys_reading" +
                "    GROUP BY patient_id, reading_type_id" +
                ") f" +
                "ON r.date = f.date" +
                "AND r.reading_type_id = f.reading_type_id" +
                "AND r.patient_id = f.patient_id;";

        return session.createNativeQuery(query, ReadingProjection.class).list();
    }

    public List<ReadingProjection> getAllLatestByPatient(Long patientId) {
        String query = "SELECT r.*" +
                "FROM sys_reading r" +
                "JOIN (" +
                "    SELECT MAX(date) AS date, reading_type_id, patient_id" +
                "    FROM sys_reading AS f" +
                "    WHERE patient_id = :patientId" +
                "    GROUP BY reading_type_id, patient_id" +
                ") f" +
                "ON r.date = f.date" +
                "AND r.reading_type_id = f.reading_type_id;";

        return session.createNativeQuery(query, ReadingProjection.class).setParameter("patientId", patientId).list();
    }

    public List<ReadingProjection> getAllLatestByType(Long readingTypeId) {
        String query = "SELECT r.*" +
                "FROM sys_reading r" +
                "JOIN (" +
                "    SELECT MAX(date) AS date, reading_type_id, patient_id" +
                "    FROM sys_reading" +
                "    WHERE reading_type_id = :readingTypeId" +
                "    GROUP BY patient_id, reading_type_id" +
                ") f" +
                "ON r.date = f.date" +
                "AND r.reading_type_id = f.reading_type_id" +
                "AND r.patient_id = f.patient_id;";

        return session.createNativeQuery(query, ReadingProjection.class).setParameter("readingTypeId", readingTypeId).list();
    }

    public List<ReadingProjection> getLatestByPatientAndType(Long patientId, Long readingTypeId) {
        String query = "SELECT r.*" +
                "FROM sys_reading r" +
                "JOIN (" +
                "    SELECT MAX(date) AS date, reading_type_id, patient_id" +
                "    FROM sys_reading" +
                "    WHERE patient_id = :patientId"+
                "       AND reading_type_id = :readingTypeId" +
                "    GROUP BY patient_id, reading_type_id" +
                ") f" +
                "ON r.date = f.date" +
                "AND r.reading_type_id = f.reading_type_id" +
                "AND r.patient_id = f.patient_id;";

        return session.createNativeQuery(query, ReadingProjection.class)
                .setParameter("patientId", patientId)
                .setParameter("readingTypeId", readingTypeId)
                .list();
    }
}
