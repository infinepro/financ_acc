package ru.maksimka.jb.converters;

import ru.maksimka.jb.dto.AccountNameDto;
import ru.maksimka.jb.entities.AccountNamesEntity;

public class AccountNameToDtoConverter implements Converter<AccountNamesEntity, AccountNameDto>{
    @Override
    public AccountNameDto convertToDto(AccountNamesEntity accountNamesEntity) {
        return new AccountNameDto()
                .withId(accountNamesEntity.getId())
                .withAccountName(accountNamesEntity.getAccountName());
    }
}
