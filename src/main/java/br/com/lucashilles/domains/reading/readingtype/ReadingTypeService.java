package br.com.lucashilles.domains.reading.readingtype;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReadingTypeService {

    public ReadingType createReadingType(String name) {
        ReadingType readingType = new ReadingType();
        readingType.name = name;
        readingType.persist();
        return readingType;
    }
}
