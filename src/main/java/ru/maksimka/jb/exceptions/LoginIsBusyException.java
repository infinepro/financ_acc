package ru.maksimka.jb.exceptions;

public class LoginIsBusyException extends MyExceptions {
    protected String message;

    public void showMessage (){
        System.err.println("\t" + this.message);
    }

    public LoginIsBusyException(String message) {
        this.message = message;
    }
}
