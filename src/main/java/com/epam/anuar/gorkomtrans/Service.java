package com.epam.anuar.gorkomtrans;

import com.epam.anuar.gorkomtrans.action.ActionResult;
import com.epam.anuar.gorkomtrans.dao.DaoFactory;
import com.epam.anuar.gorkomtrans.dao.UserDao;
import com.epam.anuar.gorkomtrans.entity.GarbageContainerType;
import com.epam.anuar.gorkomtrans.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class Service {
    public static ActionResult checkUser(String login, String password, HttpServletRequest req) {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        //todo salty hash
        User user = userDao.findByCredentials(login, password);
        if (user != null) {
            req.getSession().setAttribute("user", user);
            return new ActionResult("home", true);
        } else {
            req.setAttribute("loginError", "Incorrect login or password");
            return new ActionResult("welcome");
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
            case 3:
                req.setAttribute("registerError", "Please, fill all fields");
                return new ActionResult("register");
            default:
                req.setAttribute("registerError", "Invalid parameters");
                return new ActionResult("register");
        }
    }

    public static ActionResult update(String id, String password, String email, String firstName, String lastName, String phoneNumber,
                                      String mainAddress, String bank, String bankAccount, HttpServletRequest req) {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        switch (userDao.update(id, password, email,firstName, lastName, phoneNumber, mainAddress, bank, bankAccount)) {
            case 0:
                req.getSession(false).setAttribute("user", userDao.findById(Integer.parseInt(id)));
                return new ActionResult("personal-cabinet", true);
            case 2:
                req.setAttribute("updateUserError", "Current email is already exist");
                return new ActionResult("personal-cabinet");
            case 3:
                req.setAttribute("updateUserError", "Please, fill all fields");
                return new ActionResult("personal-cabinet");
            case 4:
                req.setAttribute("upsertCustomerError", "Please, fill all fields");
                return new ActionResult("personal-cabinet");
            default:
                req.setAttribute("updateUserError", "Invalid parameters");
                return new ActionResult("personal-cabinet");
        }
    }

    public static ActionResult fillTechSpec(String euro, String standard, String nonStandardNumber, HttpServletRequest req) {
        if (euro == null) euro = "0";
        if (standard == null) standard = "0";
        req.getSession(false).setAttribute("euro", euro);
        req.getSession(false).setAttribute("standard", standard);
        req.getSession(false).setAttribute("nonStandardNumber", nonStandardNumber);
        return new ActionResult("tech-spec", true);
    }

    public static ActionResult submitContract(String address, HttpServletRequest req) {
        return null;
    }
}
