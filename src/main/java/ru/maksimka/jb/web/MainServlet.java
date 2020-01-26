package ru.maksimka.jb.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.maksimka.jb.web.controllers.Controller;
import ru.maksimka.jb.web.controllers.LoginController;
import ru.maksimka.jb.web.controllers.RegistrationController;
import ru.maksimka.jb.web.json.LoginRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;


public class MainServlet extends HttpServlet {
    private Map<String, Controller> controllers;
    private ObjectMapper om;

    /*MainServlet() {
        controllers = new HashMap<>();
        controllers.put("/login", new LoginController());
        om = new ObjectMapper();
    }

     */

    @Override
    public void init() throws ServletException {
        super.init();
        controllers = new HashMap<>();
        controllers.put("/login", new LoginController());
        controllers.put("/registration", new RegistrationController());
        om = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = req.getRequestURI();
        Controller controller = controllers.get(url);
        System.out.println(url);


        //resp.setCharacterEncoding("UTF-8");

        if (controller == null) {
            resp.setStatus(SC_NOT_FOUND);
            return;
        }

        try {
            Object request = om.readValue(req.getInputStream(), controller.getRequestClass());
            Object response = controller.execute(request);
            om.writeValue(resp.getOutputStream(), response);
            resp.setContentType("application/json");
        } catch (Exception e) {
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}
