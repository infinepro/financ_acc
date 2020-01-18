package ru.maksimka.jb.exceptions;

import ru.maksimka.jb.Main;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        System.err.println("\t" + message);
    }

}
