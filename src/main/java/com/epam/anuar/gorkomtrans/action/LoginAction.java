package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.service.ServiceException;
import com.epam.anuar.gorkomtrans.service.UserService;
import com.epam.anuar.gorkomtrans.util.Validator;
import com.epam.anuar.gorkomtrans.util.Violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction implements Action {

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = new UserService();
        User user;
        try {
            user = userService.getLoginUser(req.getParameter("login"), req.getParameter("password"));
        } catch (ServiceException e) {
            Violation violation = Validator.InvalidLoginPass(req);
            req.setAttribute("loginError", violation.getViolation());
            return new ActionResult("welcome");
        }
        req.getSession().setAttribute("user", user);
        return new ActionResult("", true);

    }
}
