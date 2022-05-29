package br.com.lucashilles.services;

import br.com.lucashilles.domains.patient.Patient;
import br.com.lucashilles.domains.reading.Reading;
import br.com.lucashilles.domains.reading.readingtype.ReadingType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Status;
import javax.transaction.TransactionManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

@ApplicationScoped
public class ReadingsIngestService {

    @Inject
    TransactionManager transactionManager;

    public void ingestData(File fileToIngest, ReadingType readingType) {
        try {
            boolean shouldCommit = false;
            int count = 0;

            if (transactionManager.getStatus() == Status.STATUS_NO_TRANSACTION) {
                transactionManager.begin();
                shouldCommit = true;
            }

            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileToIngest));
            String line;
            bufferedReader.readLine(); // Skip the first line

            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(" ");
                Reading leitura = new Reading();

                Patient patient = Patient.find("cpf", values[0]).firstResult();

                if (patient == null) {
                    System.out.println("[INFO] Paciente não encontrado: " + values[0]);
                    continue;
                }

                leitura.patient = patient;
                leitura.readingType = readingType;
                leitura.date = new Date(Long.parseLong(values[1]) * 1000);
                leitura.value = Double.parseDouble(values[2]);
                leitura.persist();

                count ++;

                if (count % 50 == 0) {
                    transactionManager.commit();
                    transactionManager.begin();
                }
            }

            if (shouldCommit) transactionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
