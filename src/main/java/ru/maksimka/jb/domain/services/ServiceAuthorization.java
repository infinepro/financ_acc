package ru.maksimka.jb.domain.services;

import ru.maksimka.jb.domain.dto.UserDto;
import ru.maksimka.jb.domain.services.MainService;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.NotAuthorizedException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

public interface ServiceAuthorization {

    void registration(UserDto userDto) throws AlreadyExistsException;

    void checkUser(UserDto userDto) throws RecordNotFoundException, WrongUserPasswordException;

    MainService getService () throws NotAuthorizedException;
}
