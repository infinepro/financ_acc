package ru.maksimka.jb.exceptions;

public class EmptyDataAccesException extends MyExceptions{
    protected String message;

    public void showMessage (){
        System.err.println("\t" + this.message);
    }

    public EmptyDataAccesException(String message) {
        this.message = message;
    }
}
