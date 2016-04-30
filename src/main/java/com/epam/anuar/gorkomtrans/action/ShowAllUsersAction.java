package com.epam.anuar.gorkomtrans.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.anuar.gorkomtrans.service.UserService.showAllUsers;

public class ShowAllUsersAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        int page = 1;
        int recordsPerPage = 13;
        if(req.getParameter("page") != null) page = Integer.parseInt(req.getParameter("page"));
        return showAllUsers(page, recordsPerPage, req);
    }
}
