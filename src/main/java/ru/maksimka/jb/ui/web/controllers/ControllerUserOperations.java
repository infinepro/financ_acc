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

    @RequestMapping(value = "/app/change-password", method = RequestMethod.POST)
    public String changeUserPassword(String newPassword,
                                     String oldPassword, Model model) {

        String username = getAuthUserName();
        try {
            webServiceUser.changePassword(username, newPassword, oldPassword);
            model.addAttribute("change-password", "true");
            return "redirect:/";
        } catch (WrongUserPasswordException e) {
            model.addAttribute("change-password", "false");
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/app/change-email", method = RequestMethod.POST)
    public String changeUserEmail(String newEmail,
                                  String password, Model model) {

        String username = getAuthUserName();
        try {
            webServiceUser.changeEmail(username, password, newEmail);
            model.addAttribute("change-email", "true");
            return "redirect:/";
        } catch (WrongUserPasswordException e) {
            model.addAttribute("change-email", "false");
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/app/delete-profile", method = RequestMethod.POST)
    public String deleteProfile(String checkbox, String password, Model model) {

        if (checkbox != null) {
            String username = getAuthUserName();
            try {
                webServiceUser.deleteUser(username, password);
                return "redirect:/logout";
            } catch (WrongUserPasswordException e) {
                model.addAttribute("delete-profile", "false");
                return "redirect:/";
            }
        } else return "redirect:/";
    }


    private String getAuthUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}