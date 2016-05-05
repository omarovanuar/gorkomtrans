package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.service.ContractService;
import com.epam.anuar.gorkomtrans.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUserContractsAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Validator.checkUnlogged(req);
        int page = ContractService.USER_CONTRACT_PAGE;
        int recordsPerPage = ContractService.USER_CONTRACT_RECORDS;
        if(req.getParameter("page") != null) page = Integer.parseInt(req.getParameter("page"));

        User user = (User) req.getSession(false).getAttribute("user");
        ContractService contractService = new ContractService();
        List<Contract> contracts = contractService.getUserContactsPerPage(user, page, recordsPerPage);
        int noOfRecords = contracts.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (noOfPages == 0) noOfPages = 1;
        req.setAttribute("contracts", contracts);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        return new ActionResult("contracts");
    }
}
