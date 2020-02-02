package ru.maksimka.jb.services;

import ru.maksimka.jb.dto.AccountDto;
import ru.maksimka.jb.dto.TransactionDto;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.NotAuthorizedException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

import java.math.BigDecimal;

public interface Services {

    void registration(String login, String password, String email) throws AlreadyExistsException;

    AuthStatus signIn(String login, String password) throws RecordNotFoundException, WrongUserPasswordException;


    //user operations
    boolean changePassword(String newPassword) throws NotAuthorizedException;

    boolean changeEmail(String email) throws NotAuthorizedException;

    void deleteUser() throws NotAuthorizedException;


    //account operations
    boolean addNewAccount( Integer accNameId, BigDecimal balance) throws NotAuthorizedException;

    boolean deleteAccount(Integer id);

    boolean addNewAccountName(String accountName);

    boolean deleteAccountName(String accountName);


    //transaction options
    TransactionDto addNewTransaction(Integer typeId, Integer accountId, String sum);

    boolean deleteTransaction(Integer id);

}
