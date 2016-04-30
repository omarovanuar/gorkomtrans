package com.epam.anuar.gorkomtrans.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.epam.anuar.gorkomtrans.service.UserService.changeUserParameters;

public class ChangeUserParametersAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("Password", req.getParameter("Password"));
        parameters.put("Email", req.getParameter("Email"));
        parameters.put("FirstName", req.getParameter("FirstName"));
        parameters.put("LastName", req.getParameter("LastName"));
        parameters.put("PhoneNumber", req.getParameter("PhoneNumber"));
        parameters.put("MainAddress", req.getParameter("MainAddress"));
        parameters.put("Bank", req.getParameter("Bank"));
        parameters.put("BankAccount", req.getParameter("BankAccount"));
        String id = req.getParameter("id");
        return changeUserParameters(id, parameters, req);
    }
}
