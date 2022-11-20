package br.com.lucashilles.domains.patient;

import br.com.lucashilles.domains.reading.Reading;
import br.com.lucashilles.domains.reading.ReadingService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@ApplicationScoped
public class PatientService {

    @Inject
    ReadingService readingService;

    public Patient create(Patient patient) throws EntityExistsException {
        if (Patient.find("cpf = ?1", patient.cpf) == null) {
            patient.persist();
            return patient;
        }

        throw new EntityExistsException();
    }

    public void delete(Long id) {
        Patient patient = Patient.findById(id);
        if (patient != null) {
            patient.delete();
        }
    }

    public Patient update(Patient patient) throws EntityExistsException {
        if (Patient.findById(patient.id) == null) {
            patient.id = null;
            return create(patient);
        }

        return Patient.getEntityManager().merge(patient);
    }

    public List<Reading> getPatientStatus(long patientId) {
        if (Patient.findById(patientId) == null) {
            throw new EntityNotFoundException();
        }

        return readingService.getPatientStatus(patientId);
    }
}
