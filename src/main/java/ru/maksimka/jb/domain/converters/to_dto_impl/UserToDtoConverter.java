package ru.maksimka.jb.domain.converters.to_dto_impl;

import ru.maksimka.jb.domain.converters.Converter;
import ru.maksimka.jb.domain.dto.UserDto;
import ru.maksimka.jb.dao.entities.UserEntity;

public class UserToDtoConverter implements Converter<UserEntity, UserDto> {

    @Override
    public UserDto convert(UserEntity entity) {
        return new UserDto()
                    .withId(entity.getId())
                    .withUsername(entity.getName())
                    .withEmail(entity.getEmail())
                    .withPassword(entity.getPassword());

    }
}
