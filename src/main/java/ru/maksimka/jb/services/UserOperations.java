package ru.maksimka.jb.services;

import ru.maksimka.jb.DTO.AcctDTO;
import ru.maksimka.jb.DTO.TransactionsDTO;

import java.util.List;

public interface UserOperations {

    boolean addNewAcct();
    boolean addNewTypeAcct();
    boolean addNewTypeTransaction();
    boolean addNewTransaction();
    List<TransactionsDTO> getAllTransactions();
    List<AcctDTO> getAllAcct();

}
