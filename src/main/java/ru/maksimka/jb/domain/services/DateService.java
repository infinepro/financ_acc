package ru.maksimka.jb.domain.services;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class DateService {

    public java.sql.Date parseDateToSqlDate(String textDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        java.util.Date date = simpleDateFormat.parse(textDate);
        return new java.sql.Date(date.getTime());
    }
}
