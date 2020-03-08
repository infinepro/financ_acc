package ru.maksimka.jb.domain.services.console_services;

import org.springframework.beans.factory.annotation.Autowired;
import ru.maksimka.jb.dao.daoimpl.AccountDao;
import ru.maksimka.jb.dao.daoimpl.AccountNamesDao;
import ru.maksimka.jb.dao.entities.AccountEntity;
import ru.maksimka.jb.dao.entities.AccountNamesEntity;
import ru.maksimka.jb.dao.entities.UserEntity;
import ru.maksimka.jb.domain.converters.to_dto_impl.AccountNameToDtoConverter;
import ru.maksimka.jb.domain.converters.to_dto_impl.AccountToDtoConverter;
import ru.maksimka.jb.domain.dto.AccountDto;
import ru.maksimka.jb.domain.dto.AccountNameDto;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceAccountsImpl extends AbstractService implements ServiceAccounts {


    @Autowired
    private AccountDao accountDao;
    @Autowired
    private AccountNamesDao accountNamesDao;

    public ServiceAccountsImpl(UserEntity userEntity) {
        super(userEntity);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
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
    public boolean addNewAccount(Integer accNameId, BigDecimal balance) {
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

}
