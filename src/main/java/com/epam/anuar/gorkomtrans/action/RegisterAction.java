package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        return Service.registerUser(login, password, email, req);
    }


}
