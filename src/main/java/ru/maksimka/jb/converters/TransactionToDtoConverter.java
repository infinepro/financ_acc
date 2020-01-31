package ru.maksimka.jb.converters;

import ru.maksimka.jb.dto.TransactionDto;
import ru.maksimka.jb.entities.TransactionEntity;


public class TransactionToDtoConverter implements Converter<TransactionEntity, TransactionDto> {
    @Override
    public TransactionDto convertToDto(TransactionEntity transactionEntity) {
        return new TransactionDto()
                .withId(transactionEntity.getId())
                .withAccountName(transactionEntity.getAccount().getAccountName().getAccountName())
                .withCategoryName(transactionEntity.getTransactionCategory().getNameCategory())
                .withSum(transactionEntity.getSum())
                .withDate(transactionEntity.getDate());
    }
}
