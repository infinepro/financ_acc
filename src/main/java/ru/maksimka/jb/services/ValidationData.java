package ru.maksimka.jb.services;

//TODO:
import org.springframework.stereotype.Component;
import ru.maksimka.jb.exceptions.NotValidDataException;

//VALIDATION EMAIL, PASSWORD, LOGIN
@Component
public class ValidationData {

    //NOT IMPL
    public String emailValidation(String s) throws NotValidDataException {
        return s;
    }

    //NOT IMPL
    public String passwordValidation(String s) throws NotValidDataException  {
        return s;
    }
}
