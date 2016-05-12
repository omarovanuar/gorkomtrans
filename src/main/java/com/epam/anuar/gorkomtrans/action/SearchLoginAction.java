package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.service.ServiceException;
import com.epam.anuar.gorkomtrans.service.UserService;
import com.epam.anuar.gorkomtrans.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SearchLoginAction implements Action {
    private final static Logger log = LoggerFactory.getLogger(SearchLoginAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Validator.checkAdmin(req);
        int page = UserService.ALL_USER_PAGE;
        int recordsPerPage = UserService.ALL_USER_RECORDS;
        String loginPart = req.getParameter("login-search");
        List<User> users;
        try {
            users = new UserService().getUserByLoginPart(loginPart, page, recordsPerPage);
        } catch (ServiceException e) {
            log.warn("Services error: " + e.toString());
            throw new ActionException();
        }
        if (users.size() == 0) {
            req.setAttribute("noOfPages", 1);
            req.setAttribute("currentPage", 1);
            req.setAttribute("searchValue", loginPart);
            return new ActionResult("admin-cabinet");
        }
        int noOfRecords = users.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (noOfPages == 0) noOfPages = 1;
        req.setAttribute("allUsers", users);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("searchValue", loginPart);
        return new ActionResult("admin-cabinet");
    }
}
