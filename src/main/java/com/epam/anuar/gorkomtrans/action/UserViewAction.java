package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.service.UserService;
import com.epam.anuar.gorkomtrans.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserViewAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Validator.checkAdmin(req);
        UserService userService = new UserService();
        User user = userService.getViewingUser(req.getParameter("current-user"));
        req.setAttribute("userParam", user);
        return new ActionResult("user-view");
    }
}
