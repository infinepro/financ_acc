package ru.maksimka.jb.exceptions;

public class AlreadyExistsException extends MyExceptions {
    protected String message;

    public void showMessage (){
        System.err.println("\t" + this.message);
    }

    public AlreadyExistsException(String message) {
        this.message = message;
    }
}
