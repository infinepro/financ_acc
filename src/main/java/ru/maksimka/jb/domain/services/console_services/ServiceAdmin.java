package ru.maksimka.jb.domain.services.console_services;

import ru.maksimka.jb.domain.dto.UserDto;

import java.util.List;

public interface ServiceAdmin {

    public List<UserDto> getAllUsers();
}
