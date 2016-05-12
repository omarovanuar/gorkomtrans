package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.service.ServiceException;
import com.epam.anuar.gorkomtrans.service.UserService;
import com.epam.anuar.gorkomtrans.validator.ViolationException;
import com.epam.anuar.gorkomtrans.validator.Validator;
import com.epam.anuar.gorkomtrans.validator.Violation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction implements Action {
    private final static Logger log = LoggerFactory.getLogger(LoginAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        User user;
        try {
            UserService userService = new UserService();
            user = userService.getLoginUser(req.getParameter("login"), req.getParameter("password"));
        } catch (ViolationException e) {
            Violation violation = Validator.InvalidLoginPass(req);
            req.setAttribute("loginError", violation.getViolation());
            return new ActionResult("welcome");
        } catch (ServiceException e) {
            log.warn("Services error: " + e.toString());
            throw new ActionException();
        }
        req.getSession().setAttribute("user", user);
        return new ActionResult("", true);

    }
}
