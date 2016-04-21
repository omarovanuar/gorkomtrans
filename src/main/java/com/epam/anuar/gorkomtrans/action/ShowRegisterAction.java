package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ShowRegisterAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        List<String> userParamList = new ArrayList<>();
        userParamList.add("Login");
        userParamList.add("Password");
        userParamList.add("Email");
        userParamList.add("First-name");
        userParamList.add("Last-name");
        userParamList.add("Phone-number");
        userParamList.add("Main-address");
        userParamList.add("Bank");
        userParamList.add("Bank-account");
        req.setAttribute("userParamList", userParamList);
        return Service.showRegister(req);
    }


}
