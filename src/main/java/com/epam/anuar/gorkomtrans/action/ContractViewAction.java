package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.Status;
import com.epam.anuar.gorkomtrans.service.ContractService;
import com.epam.anuar.gorkomtrans.service.ServiceException;
import com.epam.anuar.gorkomtrans.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContractViewAction implements Action {
    private final static Logger log = LoggerFactory.getLogger(ContractViewAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Validator.checkUnlogged(req);
        String id = req.getParameter("current-contract");
        ContractService contractService = new ContractService();
        Contract contract;
        try {
            contract = contractService.getContractById(id);
        } catch (ServiceException e) {
            log.warn("Services error: " + e.toString());
            throw new ActionException();
        }
        req.getSession(false).setAttribute("contract", contract);
        if (contract.getStatus().equals(Status.NEW)) req.setAttribute("status", 0);
        else if (contract.getStatus().equals(Status.SUBMITTED)) req.setAttribute("status", 1);
        else req.setAttribute("status", 2);
        return new ActionResult("contract");
    }
}
