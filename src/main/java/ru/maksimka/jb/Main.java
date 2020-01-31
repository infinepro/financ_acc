package ru.maksimka.jb;

import ru.maksimka.jb.converters.Converter;
import ru.maksimka.jb.converters.UserToDtoConverter;
import ru.maksimka.jb.dao.implementations.UserDao;
import ru.maksimka.jb.dto.UserDto;
import ru.maksimka.jb.entities.UserEntity;
import ru.maksimka.jb.exceptions.AlreadyExistsException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.maksimka.jb.configurations.SpringContext.*;

public class Main {

    public static void main(String[] args) {

        UserEntity user = new UserEntity()
                .withName("newName")
                .withEmail("test@mail")
                .withPassword("new Password");

        UserDao userDao = getContext().getBean(UserDao.class);

        //UserDto userDto = new UserToDtoConverter().convertToDto(userDao.findBy("test2"));

        List<UserDto> list = userDao.findByAll().stream()
                .map(new UserToDtoConverter()::convertToDto)
                .collect(Collectors.toList());

        System.out.println(list);

    }
}
