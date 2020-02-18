package ru.maksimka.jb.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.maksimka.jb.configurations.SpringContext;
import ru.maksimka.jb.dto.UserDto;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.services.Auth;
import ru.maksimka.jb.services.AuthService;

@RestController
public class FirstController {

    @Autowired
    private Auth authService;


    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView registrationSubmit(UserDto user) {

        try {
            authService.registration(user.getName(), user.getEmail(), user.getPassword());
            ModelAndView modelAndView = new ModelAndView("hello");
            modelAndView.setStatus(HttpStatus.OK);
            return modelAndView;
        } catch (AlreadyExistsException e) {
            return new ModelAndView("index");
        }
    }

    @RequestMapping("/hello")
    public ModelAndView hello() {
        ModelAndView modelAndView = new ModelAndView("hello");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }


}
