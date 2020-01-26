package ru.maksimka.jb.web.controllers;

import org.springframework.stereotype.Component;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;
import ru.maksimka.jb.services.UserAuthService;
import ru.maksimka.jb.web.json.LoginRequest;
import ru.maksimka.jb.web.json.LoginResponse;

import static ru.maksimka.jb.SpringContext.*;

@Component("/login")
public class LoginController implements Controller<LoginRequest, LoginResponse>{
    @Override
    public LoginResponse execute(LoginRequest request) {
        UserAuthService userAuthService = getContext().getBean(UserAuthService.class);
        try {
            Integer userId = userAuthService.authUser(request.getLogin(), request.getPassword());
            if (userId != -1) {
                LoginResponse loginResponse = new LoginResponse().withAccess(true).withUserId(userId);
                return loginResponse;
            }
        } catch (WrongUserPasswordException e) {
            //ignore, im don't know that write this
        }
        return new LoginResponse().withAccess(false);
    }

    @Override
    public Class getRequestClass() {
        return LoginRequest.class;
    }
}
