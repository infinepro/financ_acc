package ru.maksimka.jb.domain.services.impl;

public interface ServiceUser {

    boolean changePassword(String newPassword) ;

    boolean changeEmail(String email) ;

    void deleteUser() ;
}
