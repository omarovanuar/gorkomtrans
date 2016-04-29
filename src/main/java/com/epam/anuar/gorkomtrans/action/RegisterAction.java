package com.epam.anuar.gorkomtrans.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.epam.anuar.gorkomtrans.action.ActionService.registerUser;

public class RegisterAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("Login", req.getParameter("Login"));
        parameters.put("Password", req.getParameter("Password"));
        parameters.put("Email", req.getParameter("Email"));
        parameters.put("FirstName", req.getParameter("FirstName"));
        parameters.put("LastName", req.getParameter("LastName"));
        parameters.put("PhoneNumber", req.getParameter("PhoneNumber"));
        parameters.put("MainAddress", req.getParameter("MainAddress"));
        parameters.put("Bank", req.getParameter("Bank"));
        parameters.put("BankAccount", req.getParameter("BankAccount"));
        return registerUser(parameters, req);
    }


}
