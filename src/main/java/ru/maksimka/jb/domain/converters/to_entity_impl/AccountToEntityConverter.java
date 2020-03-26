package ru.maksimka.jb.domain.converters.to_entity_impl;

import ru.maksimka.jb.dao.entities.AccountEntity;
import ru.maksimka.jb.dao.entities.AccountNamesEntity;
import ru.maksimka.jb.domain.converters.Converter;
import ru.maksimka.jb.domain.dto.AccountDto;

public class AccountToEntityConverter implements Converter<AccountDto, AccountEntity> {
    @Override
    public AccountEntity convert(AccountDto object) {
     /*   AccountNamesEntity accountNamesEntity = new AccountNamesEntity()
                .withId(object.getTypeId())
                .withAccountName(object.getNameAccount());
        return new AccountEntity()
                .withAccountName()
                .withBalance(object.getBalance())
                .wi
    */
        return null;
    }

}
