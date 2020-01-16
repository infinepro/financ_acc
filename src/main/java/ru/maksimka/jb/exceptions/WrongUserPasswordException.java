package ru.maksimka.jb.exceptions;

public class WrongUserPasswordException extends Exception {
    public WrongUserPasswordException() {
        System.err.println("Неверный пароль, проверьте правильность ввода");
    }

    public WrongUserPasswordException(String message) {
        System.err.println(message);
    }
}
