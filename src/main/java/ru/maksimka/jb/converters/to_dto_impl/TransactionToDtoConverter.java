package ru.maksimka.jb.converters.to_dto_impl;

import ru.maksimka.jb.converters.Converter;
import ru.maksimka.jb.dto.TransactionDto;
import ru.maksimka.jb.entities.TransactionEntity;


public class TransactionToDtoConverter implements Converter<TransactionEntity, TransactionDto> {
    @Override
    public TransactionDto convert(TransactionEntity transactionEntity) {
        return new TransactionDto()
                .withId(transactionEntity.getId())
                .withAccountName(transactionEntity.getAccount().getAccountName().getAccountName())
                .withCategoryName(transactionEntity.getTransactionCategory().getNameCategory())
                .withSum(transactionEntity.getSum())
                .withDate(transactionEntity.getDate());
    }
}
