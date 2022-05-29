package br.com.lucashilles.domains.reading;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class ReadingService {

    public List<ReadingDto> getAllByDate(String date) {
        String query = "DATE(date) = DATE(?1)";
        return Reading.find(query, date).project(ReadingDto.class).list();
    }
}
