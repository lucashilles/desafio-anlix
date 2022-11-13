package br.com.lucashilles.domains.reading;

import br.com.lucashilles.domains.patient.Patient;
import br.com.lucashilles.domains.reading.readingtype.ReadingType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.common.ProjectedFieldName;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ReadingProjection {

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

    public ReadingProjection(@ProjectedFieldName("patient.id") long patient, @ProjectedFieldName("readingType.id") long readingType, Date date, double value) {
        this.patientId = patient;
        this.readingTypeId = readingType;
        this.date = date;
        this.value = value;
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
