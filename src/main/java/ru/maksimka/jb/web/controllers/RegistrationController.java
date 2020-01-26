package ru.maksimka.jb.web.controllers;

import org.springframework.stereotype.Component;
import ru.maksimka.jb.exceptions.LoginBusyException;
import ru.maksimka.jb.services.UserAuthService;
import ru.maksimka.jb.web.json.RegistrationRequest;
import ru.maksimka.jb.web.json.RegistrationResponse;

import static ru.maksimka.jb.SpringContext.*;

@Component("/registration")
public class RegistrationController implements Controller<RegistrationRequest, RegistrationResponse> {
    @Override
    public RegistrationResponse execute(RegistrationRequest request) {
        UserAuthService userAuthService = getContext().getBean(UserAuthService.class);
        try {
            RegistrationResponse response = new RegistrationResponse().withUserId(
                    userAuthService.registerUser(request.getLogin(), request.getPassword(), request.getEmail()));

            if (response.getUserId() != -1) {
                return response.withSuccess(true);
            } else return response.withSuccess(false);
        } catch (LoginBusyException e) {
            return new RegistrationResponse().withSuccess(false);
        }
    }

    @Override
    public Class getRequestClass() {
        return RegistrationRequest.class;
    }
}
