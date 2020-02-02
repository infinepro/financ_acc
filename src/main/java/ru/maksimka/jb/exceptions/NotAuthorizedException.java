package ru.maksimka.jb.exceptions;

public class NotAuthorizedException extends MyExceptions {
    public String message;

    public NotAuthorizedException() {
    }

    public NotAuthorizedException(String  message) {
        this.message = message;
    }
}
