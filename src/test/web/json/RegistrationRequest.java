package ru.maksimka.jb.web.json;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String login;
    private String password;
    private String email;
}
