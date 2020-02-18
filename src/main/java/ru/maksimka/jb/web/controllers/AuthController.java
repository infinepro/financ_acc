package ru.maksimka.jb.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.maksimka.jb.dto.UserDto;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;
import ru.maksimka.jb.services.Auth;
import ru.maksimka.jb.services.AuthStatus;

import static ru.maksimka.jb.services.AuthStatus.AUTH;

@RestController
public class AuthController {

    @Autowired
    private Auth authService;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }

    @GetMapping("/app")
    public ModelAndView app(@RequestParam(name = "action") String action) {
        if (action.equals("reg")) {
            return new ModelAndView("registration-form");
        } else if (action.equals("auth")) {
            return new ModelAndView("sign-in-form");
        }
        return new ModelAndView("404");
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView signUp(UserDto user) {

        try {
            authService.registration(user.getName(), user.getEmail(), user.getPassword());
            ModelAndView modelAndView = new ModelAndView("registration-ok");
            modelAndView.setStatus(HttpStatus.OK);
            return modelAndView;
        } catch (AlreadyExistsException e) {
            return new ModelAndView("exist_redirect");
        }
    }

    @RequestMapping("/auth")
    public ModelAndView signIn(UserDto user) {
        ModelAndView modelAndView = new ModelAndView("sign-in-ok");
        modelAndView.setStatus(HttpStatus.OK);

        try {
            AuthStatus authStatus = authService.signIn(user.getName(), user.getPassword());
            if (authStatus == AUTH) {
                return modelAndView;
            }

        } catch (RecordNotFoundException e) {
            return new ModelAndView("not-found");
        } catch (WrongUserPasswordException e) {
            return new ModelAndView("wrong-pass");
        }

        return modelAndView;
    }


}
