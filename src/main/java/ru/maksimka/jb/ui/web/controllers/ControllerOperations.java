package ru.maksimka.jb.ui.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maksimka.jb.domain.dto.UserDto;
import ru.maksimka.jb.domain.services.impl.ServiceAdminImpl;

import java.util.List;

@RestController
public class ControllerOperations {

    @Autowired
    private ServiceAdminImpl serviceAdmin;

    @RequestMapping("/get-my-users-list")
    public List<UserDto> getAllUsers(){
        return serviceAdmin.getAllUsers();
    }
}
