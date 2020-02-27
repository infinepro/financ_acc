package ru.maksimka.jb.domain.services.impl;

import ru.maksimka.jb.domain.dto.TransactionCategoryDto;
import ru.maksimka.jb.domain.dto.TransactionDto;
import ru.maksimka.jb.exceptions.InvalidSummException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface ServiceTransactions {

    List<TransactionCategoryDto> getAllTransactionCategory();

    List<TransactionDto> getAllTransactions();

    List<TransactionDto> getAllTransactionsForDate(String date) throws ParseException;

    TransactionDto addNewTransaction(Integer transIdType, Integer accountId, BigDecimal sum)
            throws InvalidSummException, RecordNotFoundException;

    TransactionDto addNewTransaction( Integer accountId, BigDecimal sum)
            throws RecordNotFoundException;

    void addNewCategoryTransaction(String newNameCategory);

    void addNewTransactionBetweenUserAccounts(Integer fromId, Integer toId, BigDecimal sum) throws InvalidSummException;

    boolean deleteTransaction(Integer id) throws RecordNotFoundException;

    boolean cancelTransaction(Integer id) ;
}
