package br.com.lucashilles.utils;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.Provider;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Provider
public class DateParamConverter implements ParamConverter<Date> {

    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public Date fromString(String value) {
        try {
            return format.parse(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString(Date value) {
        return format.format(value);
    }
}
