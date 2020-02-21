package ru.maksimka.jb.domain.converters.to_dto_impl;

import ru.maksimka.jb.domain.converters.Converter;
import ru.maksimka.jb.domain.dto.AccountDto;
import ru.maksimka.jb.dao.entities.AccountEntity;

public class AccountToDtoConverter implements Converter<AccountEntity, AccountDto> {
    @Override
    public AccountDto convert(AccountEntity accountEntity) {
        return new AccountDto()
                .withId(accountEntity.getId())
                .withOwner(accountEntity.getOwner().getName())
                .withNameAccount(accountEntity.getAccountName().getAccountName())
                .withBalance(accountEntity.getBalance());
    }
}
