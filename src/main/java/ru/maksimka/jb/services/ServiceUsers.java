package ru.maksimka.jb.services;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.converters.to_dto_impl.AccountNameToDtoConverter;
import ru.maksimka.jb.converters.to_dto_impl.AccountToDtoConverter;
import ru.maksimka.jb.converters.to_dto_impl.TransactionCategoryToDtoConverter;
import ru.maksimka.jb.converters.to_dto_impl.TransactionToDtoConverter;
import ru.maksimka.jb.converters.to_entity_impl.UserToEntityConverter;
import ru.maksimka.jb.dao.implementations.*;
import ru.maksimka.jb.dto.*;
import ru.maksimka.jb.entities.*;
import ru.maksimka.jb.exceptions.*;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.maksimka.jb.configurations.SpringContext.*;

@Service
public class ServiceUsers implements Services {

    private UserDao userDao;
    private AccountDao accountDao;
    private AccountNamesDao accountNamesDao;
    private TransactionDao transactionDao;
    private TransactionCategoriesDao transactionCategoriesDao;
    private DateService dateService;

    private UserEntity userEntity;

    public ServiceUsers(UserDao userDao, AccountDao accountDao,
                        AccountNamesDao accountNamesDao,
                        TransactionDao transactionDao,
                        TransactionCategoriesDao transactionCategoriesDao,
                        DateService dateService) {

        this.userDao = userDao;
        this.accountDao = accountDao;
        this.accountNamesDao = accountNamesDao;
        this.transactionDao = transactionDao;
        this.transactionCategoriesDao = transactionCategoriesDao;
        this.dateService = dateService;
    }

    private void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    private void checkUserEntityForNull() throws NotAuthorizedException {
        if (this.userEntity == null) {
            throw new NotAuthorizedException("пользователь неавторизован");
        }
    }

    @Override
    public boolean changePassword(String newPassword) throws NotAuthorizedException {
        checkUserEntityForNull();
        userEntity.setPassword(newPassword);
        try {
            userDao.update(userEntity);
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean changeEmail(String newEmail) throws NotAuthorizedException {
        checkUserEntityForNull();
        userEntity.setEmail(newEmail);
        try {
            userDao.update(userEntity);
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void deleteUser() throws NotAuthorizedException {
        checkUserEntityForNull();
        try {
            userDao.delete(userEntity.getName());
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public List<AccountDto> getAllAccounts() throws NotAuthorizedException {
        checkUserEntityForNull();
        return accountDao.findByAllOnePerson(this.userEntity)
                .stream()
                .map(new AccountToDtoConverter()::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountNameDto> getAllAccountNames() {
        return accountNamesDao.findByAll()
                .stream()
                .map(new AccountNameToDtoConverter()::convert)
                .collect(Collectors.toList());
    }

    @Override
    public boolean addNewAccount(Integer accNameId, BigDecimal balance) throws NotAuthorizedException {
        checkUserEntityForNull();

        AccountNamesEntity accountNamesEntity = accountNamesDao.findBy(accNameId);
        AccountEntity accountEntity = new AccountEntity()
                .withOwner(this.userEntity)
                .withAccountName(accountNamesEntity)
                .withBalance(balance);
        accountDao.insert(accountEntity);
        return true;
    }

    @Override
    public void deleteAccount(Integer accountNameId) throws RecordNotFoundException {
        accountDao.delete(accountNameId);
    }

    @Override
    public void addNewAccountName(String accountName) throws AlreadyExistsException {
        AccountNamesEntity accountNamesEntity = new AccountNamesEntity().withAccountName(accountName);
        accountNamesDao.insert(accountNamesEntity);
    }

    @Override
    public void deleteAccountName(Integer accountNameId) throws RecordNotFoundException {
        accountNamesDao.delete(accountNameId);
    }

    @Override
    public List<TransactionCategoryDto> getAllTransactionCategory() {
        List<TransactionCategoryDto> list = transactionCategoriesDao.findByAll()
                .stream()
                .map(new TransactionCategoryToDtoConverter()::convert)
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        return transactionDao
                .findByAll()
                .stream()
                .map(new TransactionToDtoConverter()::convert)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto addNewTransaction(Integer typeId, Integer accountId, BigDecimal sum) {
        //todo: impl
        return new TransactionDto();
    }

    @Override
    public void addNewCategoryTransaction(String newNameCategory) {
        transactionCategoriesDao.insert(new TransactionCategoriesEntity().withNameCategory(newNameCategory));
    }

    @Override
    public boolean cancelTransaction(Integer id) {
        EntityManager em = getContext().getBean(EntityManager.class);
        TransactionEntity te = transactionDao.findBy(id);
        AccountEntity ae = te.getAccount();
        ae.setBalance(ae.getBalance().add(te.getSum().negate()));

        try {
            em.getTransaction().begin();
            accountDao.update(ae, em);
            transactionDao.delete(id, em);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return true;
    }

    @Override
    public boolean deleteTransaction(Integer id) throws RecordNotFoundException{
        transactionDao.delete(id);
        return true;
    }

    @Override
    public void addNewTransactionBetweenUserAccounts(Integer fromId, Integer toId, BigDecimal sum)
            throws InvalidSummException {

        List<TransactionCategoriesEntity> list = transactionCategoriesDao.findByAll();

        int idCategoryBetweenTransactions = 0;

        //search id where category name = "Внутренние переводы"
        for (TransactionCategoriesEntity tce : list) {
            if (tce.getNameCategory().equals("Внутренние переводы")) {
                idCategoryBetweenTransactions = tce.getId();
                break;
            }
        }

        //if not then add
        if (idCategoryBetweenTransactions == 0) {
            idCategoryBetweenTransactions = transactionCategoriesDao
                    .insert(new TransactionCategoriesEntity()
                            .withNameCategory("Внутренние переводы"))
                    .getId();
        }

        TransactionCategoriesEntity transactionCategoriesEntity =
                transactionCategoriesDao.findBy(idCategoryBetweenTransactions);

        EntityManager em = getContext().getBean(EntityManager.class);

        //setting a new balance for entities
        AccountEntity accFrom = accountDao.findBy(fromId);
        AccountEntity accTo = accountDao.findBy(toId);
        accFrom.setBalance(accFrom.getBalance().subtract(sum));
        accTo.setBalance(accTo.getBalance().add(sum));

        //add transaction in transaction table toTrans and From Trans
        TransactionEntity teFrom = new TransactionEntity()
                .withId(null)
                .withDate(Date.valueOf(LocalDate.now()))
                .withSum(sum.negate())
                .withTransactionCategory(transactionCategoriesEntity)
                .withAccount(accFrom);
        TransactionEntity teTo = new TransactionEntity()
                .withId(null)
                .withDate(Date.valueOf(LocalDate.now()))
                .withSum(sum)
                .withTransactionCategory(transactionCategoriesEntity)
                .withAccount(accTo);

        // check balance and transfer amount
        if (accFrom.getBalance().compareTo(sum) == -1) {
            throw new InvalidSummException();
        }

        //execute transaction
        try {
            em.getTransaction().begin();
            accountDao.update(accFrom, em);
            accountDao.update(accTo, em);
            transactionDao.insert(teFrom, em);
            transactionDao.insert(teTo, em);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    @Override
    //format date is mm.dd.yyyy
    public List<TransactionDto> getAllTransactionsForDate(String date) throws ParseException {
        List<TransactionDto> list = getAllTransactions();
        java.sql.Date sqlDate = dateService.parseDateToSqlDate(date);
        Iterator<TransactionDto> iterator = list.iterator();
        TransactionDto dto;
        List<TransactionDto> list2 = new ArrayList<>();
        while (iterator.hasNext()) {
            dto = iterator.next();
            if (dto.getDate().getTime() == sqlDate.getTime()) {
                list2.add(dto);
            }
        }

        return list2;
    }

    @Override
    public void registration(String login, String email, String password) throws AlreadyExistsException {
        UserDto userDto = new UserDto().withName(login).withEmail(email).withPassword(password);
        userDao.insert(new UserToEntityConverter().convert(userDto));
    }

    @Override
    public AuthStatus signIn(String login, String password) throws RecordNotFoundException, WrongUserPasswordException {

        UserEntity userEntity = userDao.findBy(login);
        if (userEntity == null) {
            throw new RecordNotFoundException("такого логина несуществует");
        } else if (!userEntity.getPassword().equals(password)) {
            throw new WrongUserPasswordException("неверный пароль или логин");
        } else {
            setUserEntity(userEntity);
            return AuthStatus.AUTH;
        }

    }

}
