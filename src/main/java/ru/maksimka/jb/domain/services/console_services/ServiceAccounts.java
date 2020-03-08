package ru.maksimka.jb.domain.services.console_services;

import ru.maksimka.jb.domain.dto.AccountDto;
import ru.maksimka.jb.domain.dto.AccountNameDto;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.NotAuthorizedException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceAccounts {

    List<AccountDto> getAllAccounts() throws NotAuthorizedException;

    List<AccountNameDto> getAllAccountNames();

    boolean addNewAccount(Integer accNameId, BigDecimal balance);

    void deleteAccount(Integer id) throws RecordNotFoundException;

    void addNewAccountName(String accountName) throws AlreadyExistsException;

    void deleteAccountName(Integer accountNameId) throws RecordNotFoundException;
}
