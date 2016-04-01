package com.epam.anuar.gorkomtrans.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction implements Action {
    private ActionResult welcome = new ActionResult("", true);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        return welcome;
    }
}
