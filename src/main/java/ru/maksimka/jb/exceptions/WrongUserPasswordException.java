package ru.maksimka.jb.exceptions;

import ru.maksimka.jb.Main;

public class WrongUserPasswordException extends MyExceptions {
    protected String message;

    public void showMessage (){
        System.err.println("\t" + this.message);
    }

    public WrongUserPasswordException(String message) {
        this.message = message;
    }
}
