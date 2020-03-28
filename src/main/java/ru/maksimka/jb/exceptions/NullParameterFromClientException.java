package ru.maksimka.jb.exceptions;

public class NullParameterFromClientException extends Exception {
    public NullParameterFromClientException(String s) {
        System.err.println("произошла ошибка сервера, или пришли невалидные данные, параметры ошибки:");
        System.err.println(s);
    }
}
