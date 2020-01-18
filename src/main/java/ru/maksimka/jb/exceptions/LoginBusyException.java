package ru.maksimka.jb.exceptions;

import ru.maksimka.jb.Main;

import java.sql.SQLException;

public class LoginBusyException extends Exception {
    public LoginBusyException(String message) {
        System.err.println("\t" + message);
    }
}
