package ru.maksimka.jb.exceptions;

public class UserNotFoundException extends MyExceptions {
    protected String message;

    public void showMessage (){
        System.err.println("\t" + this.message);
    }

    public UserNotFoundException(String message) {
        this.message = message;
    }
}
