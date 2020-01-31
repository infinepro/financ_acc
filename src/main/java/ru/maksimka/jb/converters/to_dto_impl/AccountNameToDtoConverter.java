package ru.maksimka.jb.converters.to_dto_impl;

import ru.maksimka.jb.converters.Converter;
import ru.maksimka.jb.dto.AccountNameDto;
import ru.maksimka.jb.entities.AccountNamesEntity;

public class AccountNameToDtoConverter implements Converter<AccountNamesEntity, AccountNameDto> {
    @Override
    public AccountNameDto convert(AccountNamesEntity accountNamesEntity) {
        return new AccountNameDto()
                .withId(accountNamesEntity.getId())
                .withAccountName(accountNamesEntity.getAccountName());
    }
}
