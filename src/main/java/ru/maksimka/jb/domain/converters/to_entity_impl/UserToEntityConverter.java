package ru.maksimka.jb.domain.converters.to_entity_impl;

import ru.maksimka.jb.domain.converters.Converter;
import ru.maksimka.jb.domain.dto.UserDto;
import ru.maksimka.jb.dao.entities.UserEntity;

public class UserToEntityConverter implements Converter<UserDto, UserEntity> {
    @Override
    public UserEntity convert(UserDto userDto) {
        return new UserEntity()
                .withId(null)
                .withName(userDto.getUsername())
                .withEmail(userDto.getEmail())
                .withPassword(userDto.getPassword());
    }
}
