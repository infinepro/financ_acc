package ru.maksimka.jb.services;

import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.NotAuthorizedException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

public interface Auth {

    void registration(String login, String password, String email) throws AlreadyExistsException;

    AuthStatus signIn(String login, String password) throws RecordNotFoundException, WrongUserPasswordException;

    Services getService () throws NotAuthorizedException;
}
