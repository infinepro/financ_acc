package ru.maksimka.jb.services;

import ru.maksimka.jb.DTO.AcctDTO;
import ru.maksimka.jb.DTO.TransactionsDTO;

import java.util.List;

public interface UserOperations<S, I> {

    boolean addNewAcct(S name, I balance, I idCategory);
    boolean addNewTypeAcct(S type);
    boolean addNewTypeTransaction(S type);
    boolean addNewTransaction();
    List<TransactionsDTO> getAllTransactions();
    List<AcctDTO> getAllAcct();
    List<String> getAllTypeAccts();

}
