package ru.maksimka.jb.domain.converters.to_dto_impl;

import ru.maksimka.jb.domain.converters.Converter;
import ru.maksimka.jb.domain.dto.AccountNameDto;
import ru.maksimka.jb.dao.entities.AccountNamesEntity;

public class AccountNameToDtoConverter implements Converter<AccountNamesEntity, AccountNameDto> {
    @Override
    public AccountNameDto convert(AccountNamesEntity accountNamesEntity) {
        return new AccountNameDto()
                .withId(accountNamesEntity.getId())
                .withAccountName(accountNamesEntity.getAccountName());
    }
}
