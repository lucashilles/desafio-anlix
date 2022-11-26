package br.com.lucashilles.services;

import br.com.lucashilles.domains.reading.readingtype.ReadingType;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.TransactionManager;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class DataIngestion {

    @ConfigProperty(name = "desafio-anlix.pacientes.location")
    String patientIngestionDirectory;

    @ConfigProperty(name = "desafio-anlix.indice-cardiaco.location")
    String cardiacIndexIngestionDirectory;

    @ConfigProperty(name = "desafio-anlix.indice-pulmonar.location")
    String pulmonaryIndexIngestionDirectory;

    @Inject
    PatientsIngestService patientsIngestService;

    @Inject
    ReadingsIngestService readingsIngestService;

    @Inject
    TransactionManager transactionManager;

    public void ingestData() {
        Log.info("Creating reading types");
        createReadingTypes();

        Log.info("Importing patient data");
        importPatientData();
        Log.info("Importing cardiac reading");
        importCardiacData();
        Log.info("Importing pulmonary reading");
        importPulmonaryData();
    }

    private void createReadingTypes() {
        try {
            if (transactionManager.getTransaction() == null) {
                transactionManager.begin();
            }

            ReadingType cardiacReading = new ReadingType();
            cardiacReading.name = "Índice Cardíaco";
            cardiacReading.persist();

            ReadingType pulmonaryReading = new ReadingType();
            pulmonaryReading.name = "Índice Pulmonar";
            pulmonaryReading.persist();

            transactionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void importPatientData() {
        URL patientResource = Thread.currentThread().getContextClassLoader().getResource(patientIngestionDirectory + "/pacientes.json");

        try {
            assert patientResource != null;
            patientsIngestService.ingestData(new File(patientResource.toURI()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void importCardiacData() {
        ReadingType cardiacReading = ReadingType.find("name", "Índice Cardíaco").firstResult();

        try {
            URI resourceDirectory = Thread.currentThread().getContextClassLoader().getResource(cardiacIndexIngestionDirectory).toURI();
            Set<File> cardiacIndexFiles = listFilesOfDirectory(resourceDirectory);

            for (File file : cardiacIndexFiles) {
                readingsIngestService.ingestData(file, cardiacReading);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void importPulmonaryData() {
        ReadingType pulmonaryReading = ReadingType.find("name", "Índice Pulmonar").firstResult();

        try {
            URI resourceDirectory = Thread.currentThread().getContextClassLoader().getResource(pulmonaryIndexIngestionDirectory).toURI();
            Set<File> pulmonaryIndexFiles = listFilesOfDirectory(resourceDirectory);

            for (File file : pulmonaryIndexFiles) {
                readingsIngestService.ingestData(file, pulmonaryReading);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Set<File> listFilesOfDirectory(URI dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::toFile)
                    .collect(Collectors.toSet());
        }
    }
}
