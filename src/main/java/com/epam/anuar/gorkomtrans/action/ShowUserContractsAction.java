package com.epam.anuar.gorkomtrans.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.anuar.gorkomtrans.service.ContractService.showUserContracts;

public class ShowUserContractsAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        int page = 1;
        int recordsPerPage = 13;
        if(req.getParameter("page") != null) page = Integer.parseInt(req.getParameter("page"));
        return showUserContracts(page, recordsPerPage, req);
    }
}
