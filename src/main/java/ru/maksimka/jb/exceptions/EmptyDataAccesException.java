package ru.maksimka.jb.exceptions;

public class EmptyDataAccesException extends Exception{
    public EmptyDataAccesException(){}

    public EmptyDataAccesException(String message) {
        System.err.println("\t" + message);
    }

}
