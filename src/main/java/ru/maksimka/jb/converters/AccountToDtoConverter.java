package ru.maksimka.jb.converters;

import ru.maksimka.jb.dto.AccountDto;
import ru.maksimka.jb.entities.AccountEntity;

public class AccountToDtoConverter implements Converter<AccountEntity, AccountDto> {
    @Override
    public AccountDto convertToDto(AccountEntity accountEntity) {
        return new AccountDto()
                .withId(accountEntity.getId())
                .withOwner(accountEntity.getOwner().getName())
                .withNameAccount(accountEntity.getAccountName().getAccountName())
                .withBalance(accountEntity.getBalance());
    }
}
