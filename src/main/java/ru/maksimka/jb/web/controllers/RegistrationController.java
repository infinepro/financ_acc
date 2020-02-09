package ru.maksimka.jb.web.controllers;

import org.springframework.stereotype.Component;
import ru.maksimka.jb.web.json.RegistrationRequest;
import ru.maksimka.jb.web.json.RegistrationResponse;

@Component("/registration")
public class RegistrationController implements Controller<RegistrationRequest, RegistrationResponse> {


    @Override
    public RegistrationResponse execute(RegistrationRequest request) {
        return null;
    }

    @Override
    public Class getRequestClass() {
        return null;
    }
}
