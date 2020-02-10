package ru.maksimka.jb.converters.to_dto_impl;

import ru.maksimka.jb.converters.Converter;
import ru.maksimka.jb.dto.UserDto;
import ru.maksimka.jb.dao.dao_entities.UserEntity;

public class UserToDtoConverter implements Converter<UserEntity, UserDto> {

    @Override
    public UserDto convert(UserEntity entity) {
        return new UserDto()
                    .withId(entity.getId())
                    .withName(entity.getName())
                    .withEmail(entity.getEmail())
                    .withPassword(entity.getPassword());

    }
}
