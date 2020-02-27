package ru.maksimka.jb.domain.services.impl;

import ru.maksimka.jb.exceptions.NotAuthorizedException;

public interface ServiceUser {

    boolean changePassword(String newPassword) throws NotAuthorizedException;

    boolean changeEmail(String email) throws NotAuthorizedException;

    void deleteUser() throws NotAuthorizedException;
}
