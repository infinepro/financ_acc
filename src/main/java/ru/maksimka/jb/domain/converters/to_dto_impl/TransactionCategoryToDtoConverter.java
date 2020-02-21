package ru.maksimka.jb.domain.converters.to_dto_impl;

import ru.maksimka.jb.domain.converters.Converter;
import ru.maksimka.jb.domain.dto.TransactionCategoryDto;
import ru.maksimka.jb.dao.entities.TransactionCategoriesEntity;

public class TransactionCategoryToDtoConverter
        implements Converter<TransactionCategoriesEntity, TransactionCategoryDto> {

    @Override
    public TransactionCategoryDto convert(TransactionCategoriesEntity transactionCategoriesEntity) {
        return new TransactionCategoryDto()
                .withId(transactionCategoriesEntity.getId())
                .withCategoryName(transactionCategoriesEntity.getNameCategory());
    }
}
