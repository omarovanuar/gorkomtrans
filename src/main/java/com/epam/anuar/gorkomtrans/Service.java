package com.epam.anuar.gorkomtrans;

import com.epam.anuar.gorkomtrans.action.ActionResult;
import com.epam.anuar.gorkomtrans.dao.DaoFactory;
import com.epam.anuar.gorkomtrans.dao.UserDao;
import com.epam.anuar.gorkomtrans.entity.User;

import javax.servlet.http.HttpServletRequest;

public class Service {
    public static ActionResult checkUser(String login, String password, HttpServletRequest req) {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        //todo salty hash
        User user = userDao.findByCredentials(login, password);
        if (user != null) {
            req.getSession().setAttribute("user", user);
            return new ActionResult("home", true);
        } else {
            req.setAttribute("loginError", "login or password incorrect");
            return new ActionResult("login");
        }
    }

    public static ActionResult registerUser(String login, String password, String email, HttpServletRequest req) {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        switch (userDao.insert(login, password, email)) {
            case 0:
                return new ActionResult("", true);
            case 1:
                req.setAttribute("registerError", "Current login is already exist");
                return new ActionResult("register");
            case 2:
                req.setAttribute("registerError", "Current email is already exist");
                return new ActionResult("register");
            default:
                req.setAttribute("registerError", "Invalid parameters");
                return new ActionResult("register");
        }
    }
}
