package br.com.lucashilles.domains.reading.readingtype;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReadingTypeService {

    public ReadingType createReadingType(ReadingType readingType) {
        readingType.persist();
        return readingType;
    }

    public ReadingType createReadingTypeByName(String name) {
        ReadingType readingType = new ReadingType();
        readingType.name = name;
        return createReadingType(readingType);
    }

    public void deleteReadingType(long id) {
        ReadingType readingType = ReadingType.findById(id);
        if (readingType != null) {
            readingType.delete();
        }
    }

    public ReadingType updateReadingType(ReadingType readingType) {
        if (ReadingType.findById(readingType.id) == null) {
            readingType.id = null;
            return createReadingType(readingType);
        }

        return ReadingType.getEntityManager().merge(readingType);
    }
}
