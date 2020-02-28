package ru.maksimka.jb.ui.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.maksimka.jb.domain.dto.UserDto;
import ru.maksimka.jb.domain.services.ServiceAuthorization;

@RestController
public class AuthController {

    private static final Logger log = LogManager.getLogger("WEB-INFO");

    @Autowired
    private ServiceAuthorization serviceAuthorizationService;


    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }

    @GetMapping("/login-error")
    public ModelAndView loginError() {
        ModelAndView modelAndView = new ModelAndView("wrong-login-or-pass");
        modelAndView.setStatus(HttpStatus.FORBIDDEN);
        return modelAndView;
    }



    @GetMapping("/app/hello")
    public ModelAndView hello() {
        return new ModelAndView("hello");
    }

    @GetMapping("/reg")
    public ModelAndView reg() {
        return new ModelAndView("registration-form");
    }

    @GetMapping("/auth")
    public ModelAndView auth() {
        return new ModelAndView("sign-in-form");
    }

    @PostMapping(value = "/reg")
    public ModelAndView signUp(UserDto user) {
      /*  try {
            System.out.println(user.toString());
            serviceAuthorizationService.registration(user);
            ModelAndView modelAndView = new ModelAndView("registration-ok");
            modelAndView.setStatus(HttpStatus.OK);
            return modelAndView;
        } catch (AlreadyExistsException e) {
            return new ModelAndView("exist_redirect");
        }*/
        ModelAndView modelAndView = new ModelAndView("registration-ok");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }

   /* @PostMapping("/auth")
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
    }*/


}
