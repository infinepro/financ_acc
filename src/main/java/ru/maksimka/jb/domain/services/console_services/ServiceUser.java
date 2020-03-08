package ru.maksimka.jb.domain.services.console_services;

public interface ServiceUser {

    boolean changePassword(String newPassword) ;

    boolean changeEmail(String email) ;

    void deleteUser() ;
}
