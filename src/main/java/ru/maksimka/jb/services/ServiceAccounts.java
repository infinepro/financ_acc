package ru.maksimka.jb.services;

import lombok.Setter;
import lombok.experimental.Accessors;
import ru.maksimka.jb.entities.AccountEntity;
import ru.maksimka.jb.entities.UserEntity;

@Setter
@Accessors(chain = true)
public class ServiceAccounts {
    private UserEntity userEntity;
    private AccountEntity accountEntity;
}
