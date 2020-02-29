package ru.maksimka.jb.ui.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import ru.maksimka.jb.exceptions.ResourceNotFoundException;

@Controller
public class ControllerErrors {


    //todo: подумать как грамотно организовать ошибки...
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleResourceNotFoundException() {
        return new ModelAndView( "404");
    }
}

