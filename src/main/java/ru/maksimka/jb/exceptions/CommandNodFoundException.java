package ru.maksimka.jb.exceptions;

public class CommandNodFoundException extends MyExceptions{

    public CommandNodFoundException(String message) {
        this.message = message;
    }

    protected String message;

    public void showMessage (){
        System.err.println("\t" + this.message);
    }




}
