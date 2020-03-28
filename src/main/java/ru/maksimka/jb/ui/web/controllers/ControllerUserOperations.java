package ru.maksimka.jb.ui.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import ru.maksimka.jb.domain.dto.AccountDto;
import ru.maksimka.jb.domain.dto.AccountNameDto;
import ru.maksimka.jb.domain.dto.TransactionCategoryDto;
import ru.maksimka.jb.domain.dto.TransactionDto;
import ru.maksimka.jb.domain.services.webservices.WebServiceUser;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.NullParameterFromClientException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<AccountDto> getUserAccountsList() {
        String username = getAuthUserName();
        List<AccountDto> list = webServiceUser.getAllAccountByNameUser(username);
        return list;
    }

    @RequestMapping(value = "/app/user-accounts", method = RequestMethod.GET)
    public String getUserAccountsPage() {
        return "user-accounts.html";
    }

    @RequestMapping(value = "/app/delete-user-account", method = RequestMethod.POST)
    public String deleteUserAccoun(String checkbox, Integer accountId, Model model) {
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
    public List<AccountNameDto> getAccountTypesList() {
        return webServiceUser.getAllTypeAccounts();
    }

    @RequestMapping(value = "/app/user-accounts/add-new-account")
    public String addNewAccount(AccountDto accountDto) {

        System.out.println(accountDto);
        String username = getAuthUserName();
        webServiceUser.addNewAccount(username, accountDto.getBalance(), accountDto.getTypeId());
        return "redirect:/app/user-accounts";
    }

    @RequestMapping(value = "/app/user-account/add-new-type-account")
    @ResponseBody
    public ResponseEntity<Object> addNewAccountType(AccountNameDto type, ModelAndView modelAndView) {
        try {
            webServiceUser.addNewTypeAccount(type.getAccountName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AlreadyExistsException e) {
            //ignore
            return new ResponseEntity<>("type already exist", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "/app/change-user-account")
    public String changeUserAccount(Integer id, Integer typeId, BigDecimal balance) {
        AccountDto accountDto = new AccountDto()
                .withTypeId(typeId)
                .withId(id)
                .withBalance(balance);
        try {
            webServiceUser.changeAccount(accountDto);
        } catch (RecordNotFoundException e) {
            //
            System.err.println(accountDto);
            e.printStackTrace();
        } finally {
            return "redirect:/app/user-accounts";
        }
    }

    @RequestMapping(value = "/app/add-new-transaction")
    public String addNewTransaction
            (Integer accountId, BigDecimal sum, Integer transactionCategoryId) {
        TransactionDto transactionDto = new TransactionDto()
                .withSum(sum)
                .withAccountId(accountId)
                .withTransactionCategoryId(transactionCategoryId);

        webServiceUser.addNewTransaction(transactionDto);
        return "redirect:/app/user-accounts";
    }

    @RequestMapping(value = "/app/user-accounts/get-category-transactions")
    @ResponseBody
    public List<TransactionCategoryDto> getCategoryTransactions() {
        return webServiceUser.getAllCategoryTransactions();
    }

    @RequestMapping(value = "/app/user-account/add-new-category-transaction")
    public void addNewCategoryTransaction(TransactionCategoryDto transactionCategoryDto) {
        System.out.println(transactionCategoryDto);
        webServiceUser.addNewCategoryTransaction(transactionCategoryDto);
    }

    @RequestMapping(value = "/app/transactions/{accountId}")
    public ModelAndView viewAccountTransactions(@PathVariable String accountId, ModelAndView modelAndView) {
        modelAndView.addObject("accountId", accountId);
        modelAndView.setViewName("user-transactions.html");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }

    @RequestMapping(value = "/app/transactions/get")
    @ResponseBody
    public ResponseEntity<List<TransactionDto>> getTransactionsByAccountId(AccountDto accountDto) {
        try {
            List<TransactionDto> list = webServiceUser.getAllTransactionsOnThisAccount(accountDto);
            return new ResponseEntity<>(list,HttpStatus.OK);
        } catch (RecordNotFoundException e) {
            e.showMessage();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/app/transaction/{id}/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> deleteTransactionByTransactionId(TransactionDto transactionDto) {
        try {
            webServiceUser.deleteTransaction(transactionDto.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RecordNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


















