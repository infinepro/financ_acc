package ru.maksimka.jb.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.maksimka.jb.dto.UserDto;

import javax.jws.WebParam;

@RestController
public class FirstController {

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }

    @RequestMapping(value="/registration", method= RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String registrationSubmit(@ModelAttribute UserDto user, Model model) {
        model.addAttribute("greeting", greeting);
        return "result";
    }

    @RequestMapping("/hello")
    public ModelAndView hello(){
        ModelAndView modelAndView = new ModelAndView("hello");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }


}
