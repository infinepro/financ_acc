package ru.maksimka.jb.exceptions;

public class RecordNotFoundException extends MyExceptions {
    protected String message;

    public void showMessage (){
        System.err.println("\t" + this.message);
    }

    public RecordNotFoundException(String message) {
        this.message = message;
    }
}
