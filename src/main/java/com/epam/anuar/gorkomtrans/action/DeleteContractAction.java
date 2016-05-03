package com.epam.anuar.gorkomtrans.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.anuar.gorkomtrans.service.ContractService.*;

public class DeleteContractAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("current-contract");
        deleteContract(id);
        return showUserContracts(1, 13, req);
    }
}
