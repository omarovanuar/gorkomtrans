package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");
        String phoneNumber = req.getParameter("phone-number");
        String mainAddress = req.getParameter("main-address");
        String bank = req.getParameter("bank");
        String bankAccount = req.getParameter("bank-account");
        return Service.update(id, password, email, firstName, lastName, phoneNumber, mainAddress, bank, bankAccount, req);
    }
}
