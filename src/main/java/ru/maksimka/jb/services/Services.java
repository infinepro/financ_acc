package ru.maksimka.jb.services;

import com.sun.corba.se.impl.protocol.RequestCanceledException;
import ru.maksimka.jb.dto.AccountDto;
import ru.maksimka.jb.dto.AccountNameDto;
import ru.maksimka.jb.dto.TransactionDto;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.NotAuthorizedException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

import java.math.BigDecimal;
import java.util.List;

public interface Services {

    void registration(String login, String password, String email) throws AlreadyExistsException;

    AuthStatus signIn(String login, String password) throws RecordNotFoundException, WrongUserPasswordException;


    //user operations
    boolean changePassword(String newPassword) throws NotAuthorizedException;

    boolean changeEmail(String email) throws NotAuthorizedException;

    void deleteUser() throws NotAuthorizedException;


    //account operations
    public List<AccountDto> getAllAccounts() throws NotAuthorizedException;

    public List<AccountNameDto> getAllAccountNames();

    boolean addNewAccount( Integer accNameId, BigDecimal balance) throws NotAuthorizedException;

    void deleteAccount(Integer id) throws RecordNotFoundException;

    void addNewAccountName(String accountName) throws AlreadyExistsException;

    void deleteAccountName(Integer accountNameId) throws RecordNotFoundException;


    //transaction options
    TransactionDto addNewTransaction(Integer typeId, Integer accountId, String sum);

    boolean deleteTransaction(Integer id);

}
