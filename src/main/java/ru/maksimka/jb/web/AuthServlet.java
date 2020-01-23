package ru.maksimka.jb.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.maksimka.jb.ContextSinglton;
import ru.maksimka.jb.DTO.AcctDTO;
import ru.maksimka.jb.containers.User;
import ru.maksimka.jb.exceptions.UserNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;
import ru.maksimka.jb.services.UserAuthService;
import ru.maksimka.jb.services.UserOperationsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static ru.maksimka.jb.web.container.SessionsContainer.getSessions;

public class AuthServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ApplicationContext context = ContextSinglton.getContext();
        UserAuthService userAuthService;
        UserOperationsService userOperationsService;

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().println("<html> " +
                "<head><title>Сервлет</title></head> <body>");


            if (!getSessions().containsKey(req.getSession().getId())) {
                resp.getWriter().print("<h2> Приветствую незнакомец</h2>");
            }


            String login = req.getParameter("login");
            String password = req.getParameter("password");
            String sessionId = req.getSession().getId();

            userAuthService = context.getBean(UserAuthService.class);
            if (!(login == "" || login == null)) {
                try {
                    if (userAuthService.authUser(login, password)) {


                        if (!getSessions().containsKey(sessionId)) {
                            getSessions().put(sessionId, login);
                        }


                        userOperationsService = context.getBean(UserOperationsService.class);
                        userOperationsService.setLogin(login);
                        List<AcctDTO> accts = userOperationsService.getAllAcct();
                        int count = 1;
                        PrintWriter writer = resp.getWriter();
                        writer.print("<ul>");
                        String tab = "&nbsp;&nbsp;&nbsp;&nbsp;";

                        if (accts.isEmpty()) {
                            writer.print("<h1> У ВАС НЕТУ СЧЕТОВ </h1>");
                        }

                        for (AcctDTO acctDTO : accts) {
                            writer.print("<li>");
                            writer.print("<i>" + count++ + "</i>" + tab);
                            writer.print(acctDTO.getNameAcct() + tab);
                            writer.print(acctDTO.getBalance() + " руб.");
                            writer.print("</li>");
                        }
                        writer.print("</ul>");

                    } else throw new UserNotFoundException();

                } catch (WrongUserPasswordException e) {
                    resp.getWriter().print("<h2> WRONG PASSWORD!! </h2>");
                } catch (UserNotFoundException e) {
                    resp.getWriter().print("<h2> USER NOT FOUND! </h2>");
                }
            } else {
                resp.getWriter().println("YOUR NOT AUTHORIZED");
            }

            resp.getWriter().println("</body></html> ");






    }
}
