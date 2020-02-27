package ru.maksimka.jb.domain.services;

import ru.maksimka.jb.dao.entities.UserEntity;
import ru.maksimka.jb.domain.dto.AccountDto;
import ru.maksimka.jb.domain.dto.AccountNameDto;
import ru.maksimka.jb.domain.dto.TransactionCategoryDto;
import ru.maksimka.jb.domain.dto.TransactionDto;
import ru.maksimka.jb.domain.services.impl.*;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.InvalidSummException;
import ru.maksimka.jb.exceptions.NotAuthorizedException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;


public class MainServiceImpl implements MainService {

    private ServiceAccounts serviceAccounts;
    private ServiceTransactions serviceTransactions;
    private ServiceUser serviceUser;

    public MainServiceImpl(UserEntity userEntity) {

        //composition
        this.serviceAccounts = new ServiceAccountsImpl(userEntity);
        this.serviceTransactions = new ServiceTransactionsImpl(userEntity);
        this.serviceUser = new ServiceUserImpl(userEntity);
    }

    //accounts methods
    @Override
    public List<AccountDto> getAllAccounts() throws NotAuthorizedException {
        return serviceAccounts.getAllAccounts();
    }

    @Override
    public List<AccountNameDto> getAllAccountNames() {
        return serviceAccounts.getAllAccountNames();
    }

    @Override
    public boolean addNewAccount(Integer accNameId, BigDecimal balance){
        return serviceAccounts.addNewAccount(accNameId, balance);
    }

    @Override
    public void deleteAccount(Integer id) throws RecordNotFoundException {
        serviceAccounts.deleteAccount(id);
    }

    @Override
    public void addNewAccountName(String accountName) throws AlreadyExistsException {
        serviceAccounts.addNewAccountName(accountName);
    }

    @Override
    public void deleteAccountName(Integer accountNameId) throws RecordNotFoundException {
        serviceAccounts.deleteAccountName(accountNameId);
    }


    //transactions methods
    @Override
    public List<TransactionCategoryDto> getAllTransactionCategory() {
        return serviceTransactions.getAllTransactionCategory();
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        return serviceTransactions.getAllTransactions();
    }

    @Override
    //format date is mm.dd.yyyy
    public List<TransactionDto> getAllTransactionsForDate(String date) throws ParseException {
        return serviceTransactions.getAllTransactionsForDate(date);
    }

    @Override
    public TransactionDto addNewTransaction(Integer transIdType, Integer accountId, BigDecimal sum) throws InvalidSummException, RecordNotFoundException {
        return serviceTransactions.addNewTransaction(transIdType, accountId, sum);
    }

    @Override
    public TransactionDto addNewTransaction(Integer accountId, BigDecimal sum) throws RecordNotFoundException {
        return serviceTransactions.addNewTransaction(accountId, sum);
    }

    @Override
    public void addNewCategoryTransaction(String newNameCategory) {
        serviceTransactions.addNewCategoryTransaction(newNameCategory);
    }

    @Override
    public void addNewTransactionBetweenUserAccounts(Integer fromId, Integer toId, BigDecimal sum) throws InvalidSummException {
        serviceTransactions.addNewTransactionBetweenUserAccounts(fromId, toId, sum);
    }

    @Override
    public boolean deleteTransaction(Integer id) throws RecordNotFoundException {
        return serviceTransactions.deleteTransaction(id);
    }

    @Override
    public boolean cancelTransaction(Integer id) {
        return serviceTransactions.cancelTransaction(id);
    }


    //user methods
    @Override
    public boolean changePassword(String newPassword) {
        return serviceUser.changePassword(newPassword);
    }

    @Override
    public boolean changeEmail(String email) {
        return serviceUser.changeEmail(email);
    }

    @Override
    public void deleteUser() {
        serviceUser.deleteUser();
    }
}
