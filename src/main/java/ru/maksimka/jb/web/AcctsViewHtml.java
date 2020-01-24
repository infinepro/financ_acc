package ru.maksimka.jb.web;

import org.springframework.context.ApplicationContext;
import ru.maksimka.jb.ContextSinglton;
import ru.maksimka.jb.DTO.AcctDTO;
import ru.maksimka.jb.services.UserOperationsService;

import java.util.List;

public class AcctsViewHtml {

    String getAcctsHtml(String login) {

        ApplicationContext context = ContextSinglton.getContext();
        UserOperationsService userOperationsService;
        StringBuilder writer = new StringBuilder();
        userOperationsService = context.getBean(UserOperationsService.class);
        userOperationsService.setLogin(login);

        List<AcctDTO> accts = userOperationsService.getAllAcct();
        int count = 1;

        writer.append("<ul>");
        String tab = "&nbsp;&nbsp;&nbsp;&nbsp;";

        if (accts.isEmpty()) {
            writer.append("<h1> У ВАС НЕТУ СЧЕТОВ </h1>");
        }

        for (AcctDTO acctDTO : accts) {
            writer.append("<li> " + count++ + tab +
                    acctDTO.getNameAcct() + tab +
                    acctDTO.getBalance() + " руб.</li>");
        }
        writer.append("</ul>");
        return writer.toString();
    }

}
