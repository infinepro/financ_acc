package ru.maksimka.jb.domain.services;

import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.NotAuthorizedException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

public interface ServiceAuthorization {

    void registration(String login, String password, String email) throws AlreadyExistsException;

    StatusAuthorization signIn(String login, String password) throws RecordNotFoundException, WrongUserPasswordException;

    MainService getService () throws NotAuthorizedException;
}
