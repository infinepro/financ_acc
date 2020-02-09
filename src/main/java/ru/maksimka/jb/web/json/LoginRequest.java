package ru.maksimka.jb.web.json;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String password;
}