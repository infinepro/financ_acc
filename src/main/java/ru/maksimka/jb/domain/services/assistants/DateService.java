package ru.maksimka.jb.domain.services.assistants;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class DateService {

    public java.sql.Date parseDateToSqlDate(String textDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        java.util.Date date = simpleDateFormat.parse(textDate);
        return new java.sql.Date(date.getTime());
    }
}
