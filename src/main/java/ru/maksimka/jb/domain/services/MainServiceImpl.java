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

    private UserEntity userEntity;
    private ServiceAccounts serviceAccounts;
    private ServiceTransactions serviceTransactions;
    private ServiceUser serviceUser;

    public MainServiceImpl(UserEntity userEntity) {
        this.userEntity = userEntity;

        //composition
        this.serviceAccounts = new ServiceAccountsImpl(userEntity);
        this.serviceTransactions = new ServiceTransactionsImpl(userEntity);
        this.serviceUser = new ServiceUserImpl(userEntity);
    }

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

    }

    @Override
    public void addNewAccountName(String accountName) throws AlreadyExistsException {

    }

    @Override
    public void deleteAccountName(Integer accountNameId) throws RecordNotFoundException {

    }

    @Override
    public List<TransactionCategoryDto> getAllTransactionCategory() {
        return null;
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        return null;
    }

    @Override
    public List<TransactionDto> getAllTransactionsForDate(String date) throws ParseException {
        return null;
    }

    @Override
    public TransactionDto addNewTransaction(Integer transIdType, Integer accountId, BigDecimal sum) throws InvalidSummException, RecordNotFoundException {
        return null;
    }

    @Override
    public TransactionDto addNewTransaction(Integer accountId, BigDecimal sum) throws RecordNotFoundException {
        return null;
    }

    @Override
    public void addNewCategoryTransaction(String newNameCategory) {

    }

    @Override
    public void addNewTransactionBetweenUserAccounts(Integer fromId, Integer toId, BigDecimal sum) throws InvalidSummException {

    }

    @Override
    public boolean deleteTransaction(Integer id) throws RecordNotFoundException {
        return false;
    }

    @Override
    public boolean cancelTransaction(Integer id) {
        return false;
    }

    @Override
    public boolean changePassword(String newPassword) throws NotAuthorizedException {
        return false;
    }

    @Override
    public boolean changeEmail(String email) throws NotAuthorizedException {
        return false;
    }

    @Override
    public void deleteUser() throws NotAuthorizedException {

    }
}
