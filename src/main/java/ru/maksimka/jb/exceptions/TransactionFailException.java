package ru.maksimka.jb.exceptions;

public class TransactionFailException extends MyExceptions{
    protected String message;

    public void showMessage (){
        System.err.println("\t" + this.message);
    }

    public TransactionFailException(String message) {
        this.message = message;
    }
}
