package br.com.lucashilles.services;

import br.com.lucashilles.domains.patient.Patient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Status;
import javax.transaction.TransactionManager;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

@ApplicationScoped
public class PatientsIngestService {

    @Inject
    TransactionManager transactionManager;

    public void ingestData(File fileToIngest) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        objectMapper.setDateFormat(dateFormat);

        List<Patient> patients;

        try {
            boolean shouldCommit = false;
            if (transactionManager.getStatus() == Status.STATUS_NO_TRANSACTION) {
                transactionManager.begin();
                shouldCommit = true;
            }

            patients = objectMapper.readValue(fileToIngest, new TypeReference<List<Patient>>() {});
            patients.forEach(paciente -> {
                paciente.persist();
            });

            if (shouldCommit) transactionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
