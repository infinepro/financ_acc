package ru.maksimka.jb.exceptions;

import ru.maksimka.jb.Main;

public class WrongUserPasswordException extends Exception {
    public WrongUserPasswordException() {
    }

    public WrongUserPasswordException(String message) {
        System.err.println("\t" + message);
    }
}
