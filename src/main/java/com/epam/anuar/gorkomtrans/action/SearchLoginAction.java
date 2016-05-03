package com.epam.anuar.gorkomtrans.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.anuar.gorkomtrans.service.UserService.searchByLogin;

public class SearchLoginAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        int page = 1;
        int recordsPerPage = 13;
        String loginPart = req.getParameter("login-search");
        return searchByLogin(loginPart, page, recordsPerPage, req);
    }
}
