package ru.maksimka.jb.services;

import ru.maksimka.jb.exceptions.LoginBusyException;
import ru.maksimka.jb.exceptions.UserNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

public interface UserAuth<L, P> {

    boolean authUser(L login, L password) throws WrongUserPasswordException, UserNotFoundException;
    boolean registerUser(L login, L password,  L email) throws LoginBusyException ;

}
