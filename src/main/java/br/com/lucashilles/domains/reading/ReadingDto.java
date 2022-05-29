package br.com.lucashilles.domains.reading;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.common.ProjectedFieldName;

import java.util.Date;

public class ReadingDto {

    @JsonProperty("patient_id")
    public long patientId;

    @JsonProperty("reading_type_id")
    public long readingTypeId;

    public Date date;

    public double value;

    public ReadingDto(@ProjectedFieldName("patient.id") long patient, @ProjectedFieldName("readingType.id") long readingType, Date date, double value) {
        this.patientId = patient;
        this.readingTypeId = readingType;
        this.date = date;
        this.value = value;
    }
}
