package br.com.lucashilles.domains.reading;

import br.com.lucashilles.domains.patient.Patient;
import br.com.lucashilles.domains.reading.readingtype.ReadingType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.validation.constraints.NotNull;
import java.util.Date;

@RegisterForReflection
public class ReadingProjection {

    public long id;

    @JsonProperty("patient_id")
    @NotNull
    public long patientId;

    @JsonProperty("reading_type_id")
    @NotNull
    public long readingTypeId;

    @NotNull
    public Date date;

    @NotNull
    public double value;

    public ReadingProjection(Reading reading) {
        this.id = reading.id;
        this.patientId = reading.patient.id;
        this.readingTypeId = reading.readingType.id;
        this.date = reading.date;
        this.value = reading.value;
    }

    public Reading toEntity() {
        Reading reading = new Reading();
        Patient patient = new Patient();
        ReadingType readingType = new ReadingType();

        patient.id = patientId;
        readingType.id = readingTypeId;
        reading.setPatient(patient);
        reading.readingType = readingType;
        reading.setDate(date);
        reading.setValue(value);

        return reading;
    }
}
