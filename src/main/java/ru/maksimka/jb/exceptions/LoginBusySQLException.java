package ru.maksimka.jb.exceptions;

import java.sql.SQLException;

public class LoginBusySQLException extends SQLException {
    public LoginBusySQLException(String message) {
        System.err.println(message);
    }
}
