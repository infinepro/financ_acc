package ru.maksimka.jb.converters;

import ru.maksimka.jb.dto.TransactionCategoryDto;
import ru.maksimka.jb.entities.TransactionCategoriesEntity;

public class TransactionCategoryToDtoConverter
        implements Converter<TransactionCategoriesEntity, TransactionCategoryDto> {

    @Override
    public TransactionCategoryDto convertToDto(TransactionCategoriesEntity transactionCategoriesEntity) {
        return new TransactionCategoryDto()
                .withId(transactionCategoriesEntity.getId())
                .withCategoryName(transactionCategoriesEntity.getNameCategory());
    }
}
