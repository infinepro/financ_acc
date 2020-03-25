package ru.maksimka.jb.ui.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.maksimka.jb.dao.entities.AccountEntity;
import ru.maksimka.jb.domain.dto.AccountDto;
import ru.maksimka.jb.domain.dto.AccountNameDto;
import ru.maksimka.jb.domain.services.webservices.WebServiceUser;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class ControllerUserOperations {

    private WebServiceUser webServiceUser;

    @Autowired
    public ControllerUserOperations(WebServiceUser webServiceUser) {
        this.webServiceUser = webServiceUser;
    }

    private String getAuthUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @RequestMapping(value = "/app/change-password", method = RequestMethod.POST)
    public String changeUserPassword(String newPassword, String oldPassword, Model model) {
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
    public String changeUserEmail(String newEmail, String password, Model model) {
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

    @RequestMapping(value = "/app/user-accounts/get")
    @ResponseBody
    public List<AccountDto> getUserAccountsData() {
        String username = getAuthUserName();
        List<AccountDto> list = webServiceUser.getAllAccountByNameUser(username);
        System.out.println(list);
        return list;
    }

    @RequestMapping(value = "/app/user-accounts", method = RequestMethod.GET)
    public String getUserAccounts() {
        return "user-accounts.html";
    }

    @RequestMapping(value = "/app/delete-user-account", method = RequestMethod.POST)
    public String deleteUserAAccount(String checkbox, Integer accountId, Model model) {
        if (checkbox != null) {
            try {
                webServiceUser.deleteUserAccountById(accountId);
            } catch (RecordNotFoundException e) {
                model.addAttribute("delete-account", "false");
            }
        }
        return "redirect:/app/user-accounts";
    }

    @RequestMapping(value = "/app/user-accounts/get-types-accounts")
    @ResponseBody
    public List<AccountNameDto> getTypesAccounts() {
        return webServiceUser.getAllTypeAccounts();
    }

    @RequestMapping(value = "/app/user-accounts/add-new-account")
    public String addNewAccount(Integer id, Integer balance) {
        System.out.println(id + "" + balance);
        String username = getAuthUserName();
        webServiceUser.addNewAccount(username, balance, id);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!" + username + "  " + balance + "   " + id);
        return "redirect:/app/user-accounts";
    }
}
