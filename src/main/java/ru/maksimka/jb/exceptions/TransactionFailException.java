package ru.maksimka.jb.exceptions;

import ru.maksimka.jb.Main;

public class TransactionFailException extends Exception{
    public TransactionFailException (String message) {
        System.err.println("\t" + message);
    }

    public TransactionFailException() {

    }
}
