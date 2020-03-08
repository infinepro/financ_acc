package ru.maksimka.jb.domain.services.console_services;

import org.springframework.stereotype.Component;
import ru.maksimka.jb.dao.daoimpl.UserDao;
import ru.maksimka.jb.domain.converters.to_dto_impl.UserToDtoConverter;
import ru.maksimka.jb.domain.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceAdminImpl {

    private UserDao userDao;

    public ServiceAdminImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<UserDto> getAllUsers() {
        return userDao.findByAll().stream().map(new UserToDtoConverter()::convert).collect(Collectors.toList());
    }
}
