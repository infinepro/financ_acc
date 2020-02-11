package ru.maksimka.jb.services;

import ru.maksimka.jb.dto.AccountDto;
import ru.maksimka.jb.dto.AccountNameDto;
import ru.maksimka.jb.dto.TransactionCategoryDto;
import ru.maksimka.jb.dto.TransactionDto;
import ru.maksimka.jb.exceptions.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface Services {

    //user operations
    boolean changePassword(String newPassword) throws NotAuthorizedException;

    boolean changeEmail(String email) throws NotAuthorizedException;

    void deleteUser() throws NotAuthorizedException;


    //account operations
    List<AccountDto> getAllAccounts() throws NotAuthorizedException;

    List<AccountNameDto> getAllAccountNames();

    boolean addNewAccount(Integer accNameId, BigDecimal balance) throws NotAuthorizedException;

    void deleteAccount(Integer id) throws RecordNotFoundException;

    void addNewAccountName(String accountName) throws AlreadyExistsException;

    void deleteAccountName(Integer accountNameId) throws RecordNotFoundException;

    //transaction options
    List<TransactionCategoryDto> getAllTransactionCategory();

    List<TransactionDto> getAllTransactions();

    List<TransactionDto> getAllTransactionsForDate(String date) throws ParseException;

    TransactionDto addNewTransaction(Integer transIdType, Integer accountId, BigDecimal sum)
            throws InvalidSummException, RecordNotFoundException;

    void addNewCategoryTransaction(String newNameCategory);

    void addNewTransactionBetweenUserAccounts(Integer fromId, Integer toId, BigDecimal sum) throws InvalidSummException;

    boolean deleteTransaction(Integer id) throws RecordNotFoundException;

    boolean cancelTransaction(Integer id) ;

}
