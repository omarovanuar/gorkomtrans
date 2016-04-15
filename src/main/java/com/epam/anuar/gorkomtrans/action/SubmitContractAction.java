package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.Service;
import com.epam.anuar.gorkomtrans.entity.Contract;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.anuar.gorkomtrans.Service.sanctionContract;

public class SubmitContractAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String contractCost = req.getParameter("contract-cost");
        Contract contract = (Contract) req.getAttribute("contract");
        return sanctionContract(contractCost, contract, req);
    }
}
