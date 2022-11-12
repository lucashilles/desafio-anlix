package br.com.lucashilles.domains.patient;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityExistsException;

@ApplicationScoped
public class PatientService {

    public Patient createPatient(Patient patient) throws EntityExistsException {
        if (Patient.find("cpf = ?1", patient.cpf) == null) {
            patient.persist();
            return patient;
        }

        throw new EntityExistsException();
    }

    public void deletePatient(Long id) {
        Patient patient = Patient.findById(id);
        if (patient != null) {
            patient.delete();
        }
    }

    public Patient updatePatient(Patient patient) throws EntityExistsException {
        if (Patient.findById(patient.id) == null) {
            patient.id = null;
            return createPatient(patient);
        }

        return Patient.getEntityManager().merge(patient);
    }
}
