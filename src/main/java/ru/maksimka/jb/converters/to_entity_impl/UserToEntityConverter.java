package ru.maksimka.jb.converters.to_entity_impl;

import ru.maksimka.jb.converters.Converter;
import ru.maksimka.jb.dto.UserDto;
import ru.maksimka.jb.dao.dao_entities.UserEntity;

public class UserToEntityConverter implements Converter<UserDto, UserEntity> {
    @Override
    public UserEntity convert(UserDto userDto) {
        return new UserEntity()
                .withId(null)
                .withName(userDto.getName())
                .withEmail(userDto.getName())
                .withPassword(userDto.getPassword());
    }
}
