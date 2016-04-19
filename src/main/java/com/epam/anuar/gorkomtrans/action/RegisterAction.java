package com.epam.anuar.gorkomtrans.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.epam.anuar.gorkomtrans.Service.registerUser;

public class RegisterAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        List<String> parameters = new ArrayList<>();
        parameters.add(req.getParameter("Login"));
        parameters.add(req.getParameter("Password"));
        parameters.add(req.getParameter("Email"));
        parameters.add(req.getParameter("First-name"));
        parameters.add(req.getParameter("Last-name"));
        parameters.add(req.getParameter("Phone-number"));
        parameters.add(req.getParameter("Main-address"));
        parameters.add(req.getParameter("Bank"));
        parameters.add(req.getParameter("Bank-account"));
        return registerUser(parameters, req);
    }


}
