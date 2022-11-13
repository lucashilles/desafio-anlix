package br.com.lucashilles.domains.reading.readingtype;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReadingTypeService {

    public ReadingType create(ReadingType readingType) {
        readingType.persist();
        return readingType;
    }

    public ReadingType create(String name) {
        ReadingType readingType = new ReadingType();
        readingType.name = name;

        return create(readingType);
    }

    public void delete(long id) {
        ReadingType readingType = ReadingType.findById(id);
        if (readingType != null) {
            readingType.delete();
        }
    }

    public ReadingType update(ReadingType readingType) {
        if (ReadingType.findById(readingType.id) == null) {
            readingType.id = null;
            return create(readingType);
        }

        return ReadingType.getEntityManager().merge(readingType);
    }
}
