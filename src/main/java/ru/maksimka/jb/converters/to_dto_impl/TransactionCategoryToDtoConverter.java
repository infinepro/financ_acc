package ru.maksimka.jb.converters.to_dto_impl;

import ru.maksimka.jb.converters.Converter;
import ru.maksimka.jb.dto.TransactionCategoryDto;
import ru.maksimka.jb.dao.dao_entities.TransactionCategoriesEntity;

public class TransactionCategoryToDtoConverter
        implements Converter<TransactionCategoriesEntity, TransactionCategoryDto> {

    @Override
    public TransactionCategoryDto convert(TransactionCategoriesEntity transactionCategoriesEntity) {
        return new TransactionCategoryDto()
                .withId(transactionCategoriesEntity.getId())
                .withCategoryName(transactionCategoriesEntity.getNameCategory());
    }
}
