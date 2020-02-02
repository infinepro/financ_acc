package ru.maksimka.jb.services;

import lombok.Setter;
import lombok.experimental.Accessors;
import ru.maksimka.jb.entities.TransactionEntity;
import ru.maksimka.jb.entities.UserEntity;

@Setter
@Accessors(chain = true)
public class ServiceTransactions {
    private UserEntity userEntity;
    private TransactionEntity transactionEntity;
}
