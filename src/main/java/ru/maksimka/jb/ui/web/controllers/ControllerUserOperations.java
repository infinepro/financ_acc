package ru.maksimka.jb.ui.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.maksimka.jb.domain.services.webservices.WebServiceUser;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

@Controller
public class ControllerUserOperations {

    @Autowired
    private WebServiceUser webServiceUser;

    @RequestMapping(value = "/app/change-password", method = RequestMethod.GET)
    public String changeUserPassword() {
        return "change-user-password.jsp";
    }

    @RequestMapping(value = "/app/change-password", method = RequestMethod.POST)
    public String changeUserPassword(String newPassword,
                                     String oldPassword, Model model) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            webServiceUser.changePassword(userName, newPassword, oldPassword);
            model.addAttribute("change-password", "true");
            return "redirect:/";
        } catch (WrongUserPasswordException e) {
            model.addAttribute("change-password", "false");
            return "redirect:/app/change-password";
        }

    }
}
