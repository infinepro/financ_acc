package ru.maksimka.jb.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FirstController {

    @RequestMapping("/hello")
    public ModelAndView hello(){
        ModelAndView modelAndView = new ModelAndView("hello");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }
}
