package ru.maksimka.jb.web.controllers;

import org.springframework.stereotype.Component;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;
import ru.maksimka.jb.web.json.LoginRequest;
import ru.maksimka.jb.web.json.LoginResponse;

@Component("/login")
public class LoginController implements Controller<LoginRequest, LoginResponse>{
    @Override
    public LoginResponse execute(LoginRequest request) {
        return null;
    }

    @Override
    public Class getRequestClass() {
        return null;
    }
}
