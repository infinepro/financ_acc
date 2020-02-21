package ru.maksimka.jb.domain.converters.to_dto_impl;

import ru.maksimka.jb.domain.converters.Converter;
import ru.maksimka.jb.domain.dto.TransactionDto;
import ru.maksimka.jb.dao.entities.TransactionEntity;


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
