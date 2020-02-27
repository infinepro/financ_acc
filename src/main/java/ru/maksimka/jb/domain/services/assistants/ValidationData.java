package ru.maksimka.jb.domain.services.assistants;

//TODO:

import org.springframework.stereotype.Service;
import ru.maksimka.jb.exceptions.NotValidDataException;

//VALIDATION EMAIL, PASSWORD, LOGIN
@Service
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
