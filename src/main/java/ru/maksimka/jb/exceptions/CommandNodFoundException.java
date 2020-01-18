package ru.maksimka.jb.exceptions;

import ru.maksimka.jb.Main;

public class CommandNodFoundException extends Exception{
    public CommandNodFoundException() {
    }

    public CommandNodFoundException(String message) {
        System.err.println("\t" + message);
    }
}
