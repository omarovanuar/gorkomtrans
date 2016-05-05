package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.service.UserService;
import com.epam.anuar.gorkomtrans.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAllUsersAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Validator.checkAdmin(req);
        int page = UserService.ALL_USER_PAGE;
        int recordsPerPage = UserService.ALL_USER_RECORDS;
        if(req.getParameter("page") != null) page = Integer.parseInt(req.getParameter("page"));
        UserService userService = new UserService();
        List<User> users = userService.getAllUsersPerPage(page, recordsPerPage);
        int noOfRecords = users.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (noOfPages == 0) noOfPages = 1;
        req.setAttribute("allUsers", users);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        return new ActionResult("admin-cabinet");
    }
}
