package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.Status;
import com.epam.anuar.gorkomtrans.service.ContractService;
import com.epam.anuar.gorkomtrans.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.ResourceBundle;

public class DenyContractAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Validator.checkAdminOrModer(req);
        Contract contract = (Contract) req.getSession(false).getAttribute("contract");
        ContractService contractService = new ContractService();
        contractService.updateContractStatus(contract.getId(), Status.DENIED);
        ResourceBundle bundle = ResourceBundle.getBundle("other-text", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
        req.setAttribute("statusMessage", bundle.getString("status.denied"));
        return new ActionResult("contract-status");
    }
}
