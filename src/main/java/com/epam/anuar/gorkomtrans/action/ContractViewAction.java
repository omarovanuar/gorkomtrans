package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.Status;
import com.epam.anuar.gorkomtrans.service.ContractService;
import com.epam.anuar.gorkomtrans.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContractViewAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Validator.checkUnlogged(req);
        String id = req.getParameter("current-contract");
        ContractService contractService = new ContractService();
        Contract contract = contractService.getContractById(id);
        req.getSession(false).setAttribute("contract", contract);
        if (contract.getStatus().equals(Status.NEW)) req.setAttribute("status", 0);
        else if (contract.getStatus().equals(Status.SUBMITTED)) req.setAttribute("status", 1);
        else req.setAttribute("status", 2);
        return new ActionResult("contract");
    }
}
