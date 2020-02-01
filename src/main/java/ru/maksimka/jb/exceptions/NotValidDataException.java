package ru.maksimka.jb.exceptions;

public class NotValidDataException extends MyExceptions{
    public String message;

    public NotValidDataException() {
    }

    public NotValidDataException(String  message) {
        this.message = message;
    }
}
