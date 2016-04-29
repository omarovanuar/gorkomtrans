package com.epam.anuar.gorkomtrans.action;

import sun.util.resources.ru.LocaleNames_ru;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class SetEnLanguage implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession(false).setAttribute("locale", "en");
        return new ActionResult("", true);
    }
}
