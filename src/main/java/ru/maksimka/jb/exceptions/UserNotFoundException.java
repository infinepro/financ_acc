package ru.maksimka.jb.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        System.err.println("Пользователь с таким логином не найден, провертьте правильность ввода");

    }

    public UserNotFoundException(String message) {
        System.err.println(message);
    }

}
