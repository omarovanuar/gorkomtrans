package com.epam.anuar.gorkomtrans.util;

import com.epam.anuar.gorkomtrans.action.UnloggedException;
import com.epam.anuar.gorkomtrans.entity.RoleType;
import com.epam.anuar.gorkomtrans.entity.User;

import javax.servlet.http.HttpServletRequest;

public class Validator {
    public static void checkUnlogged(HttpServletRequest req) {
        if (req.getSession(false).getAttribute("user") == null) {
            throw new UnloggedException("Please, login");
        }
    }

    public static void checkAdmin(HttpServletRequest req) {
        User user = (User) req.getSession(false).getAttribute("user");
        if (!(user.getRole().equals(RoleType.ADMIN))) {
            System.out.println(user.getRole().toString());
            throw new UnloggedException("You have not admin role");
        }
    }

    public static void checkAdminOrModer(HttpServletRequest req) {
        User user = (User) req.getSession(false).getAttribute("user");
        if (!(user.getRole().equals(RoleType.ADMIN) || user.getRole().equals(RoleType.MODERATOR))) {
            throw new UnloggedException("You have not admin or moderator role");
        }
    }

    public static void isEmptyTechSpec(String euro, String standard, String nonStandardNumber) {
        if (euro == null && standard == null && nonStandardNumber.equals("0")) {
            throw new RuntimeException("Please, choose type of containers");
        }
    }
}
