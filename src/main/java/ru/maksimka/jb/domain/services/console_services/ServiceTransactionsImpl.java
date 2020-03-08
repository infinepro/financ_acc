package ru.maksimka.jb.domain.services.console_services;


import org.springframework.beans.factory.annotation.Autowired;
import ru.maksimka.jb.dao.daoimpl.AccountDao;
import ru.maksimka.jb.dao.daoimpl.TransactionCategoriesDao;
import ru.maksimka.jb.dao.daoimpl.TransactionDao;
import ru.maksimka.jb.dao.entities.AccountEntity;
import ru.maksimka.jb.dao.entities.TransactionCategoriesEntity;
import ru.maksimka.jb.dao.entities.TransactionEntity;
import ru.maksimka.jb.dao.entities.UserEntity;
import ru.maksimka.jb.domain.converters.to_dto_impl.TransactionCategoryToDtoConverter;
import ru.maksimka.jb.domain.converters.to_dto_impl.TransactionToDtoConverter;
import ru.maksimka.jb.domain.dto.TransactionCategoryDto;
import ru.maksimka.jb.domain.dto.TransactionDto;
import ru.maksimka.jb.domain.services.assistants.DateService;
import ru.maksimka.jb.exceptions.InvalidSummException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceTransactionsImpl extends AbstractService implements ServiceTransactions {

    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private TransactionCategoriesDao transactionCategoriesDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private DateService dateService;
    @Autowired
    private EntityManager em;

    public ServiceTransactionsImpl(UserEntity userEntity) {
        super(userEntity);
    }

    @Override
    public List<TransactionCategoryDto> getAllTransactionCategory() {
        return transactionCategoriesDao.findByAll()
                .stream()
                .map(new TransactionCategoryToDtoConverter()::convert)
                .collect(Collectors.toList());
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
    public void addNewCategoryTransaction(String newNameCategory) {
        transactionCategoriesDao.insert(new TransactionCategoriesEntity().withNameCategory(newNameCategory));
    }

    @Override
    public boolean cancelTransaction(Integer id) {
        EntityManager em = this.em;
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
    public boolean deleteTransaction(Integer id) throws RecordNotFoundException {
        transactionDao.delete(id);
        return true;
    }

    @Override
    public TransactionDto addNewTransaction(Integer accountId, BigDecimal sum) throws RecordNotFoundException {
        EntityManager em = this.em;

        AccountEntity ae = accountDao.findBy(accountId);
        ae.setBalance(ae.getBalance().subtract(sum));

        List<TransactionCategoriesEntity> list = transactionCategoriesDao.findByAll();

        //проверка наличия типа "Пополнение"
        int transTypeId = 0;
        for (TransactionCategoriesEntity tce : list) {
            if (tce.getNameCategory().equals("Пополнение")) {
                transTypeId = tce.getId();
                break;
            }
        }

        //if not then add
        if (transTypeId == 0) {
            transTypeId = transactionCategoriesDao
                    .insert(new TransactionCategoriesEntity()
                            .withNameCategory("Пополнение"))
                    .getId();
        }


        TransactionCategoriesEntity tce = transactionCategoriesDao.findBy(transTypeId);
        TransactionEntity te = new TransactionEntity()
                .withDate(Date.valueOf(LocalDate.now()))
                .withAccount(ae)
                .withSum(sum)
                .withTransactionCategory(tce);

        em.getTransaction().begin();
        transactionDao.insert(te, em);
        accountDao.update(ae, em);
        return new TransactionDto();
    }

    @Override
    public TransactionDto addNewTransaction(Integer transTypeId,
                                            Integer accountId,
                                            BigDecimal sum)
            throws InvalidSummException, RecordNotFoundException {

        EntityManager em = this.em;

        AccountEntity ae = accountDao.findBy(accountId);
        //если сумма отрицательная
        if (sum.intValue() < 0) {
            //проверяем если сумма больше средств на счете или если сумма == 0
            if (ae.getBalance().intValue() < sum.negate().intValue() || sum.intValue() == 0) {
                throw new InvalidSummException();
            }
            sum = sum.negate();
        }

        ae.setBalance(ae.getBalance().subtract(sum));

        TransactionCategoriesEntity tce = transactionCategoriesDao.findBy(transTypeId);
        TransactionEntity te = new TransactionEntity()
                .withDate(Date.valueOf(LocalDate.now()))
                .withAccount(ae)
                .withSum(sum)
                .withTransactionCategory(tce);

        em.getTransaction().begin();
        transactionDao.insert(te, em);
        accountDao.update(ae, em);
        return new TransactionDto();
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

        EntityManager em = this.em;

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
        if (accFrom.getBalance().compareTo(sum) < 0) {
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
}
