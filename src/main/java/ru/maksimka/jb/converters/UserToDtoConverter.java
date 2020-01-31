package ru.maksimka.jb.converters;

import ru.maksimka.jb.dto.UserDto;
import ru.maksimka.jb.entities.UserEntity;

public class UserToDtoConverter implements Converter<UserEntity, UserDto> {

    @Override
    public UserDto convertToDto(UserEntity entity) {
        return new UserDto()
                    .withId(entity.getId())
                    .withName(entity.getName())
                    .withEmail(entity.getEmail())
                    .withPassword(entity.getPassword());

    }
}
