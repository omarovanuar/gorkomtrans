package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowUserContractsAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        int page = 1;
        int recordsPerPage = 13;
        if(req.getParameter("page") != null) page = Integer.parseInt(req.getParameter("page"));
        return Service.showUserContracts(page, recordsPerPage, req);
    }
}
