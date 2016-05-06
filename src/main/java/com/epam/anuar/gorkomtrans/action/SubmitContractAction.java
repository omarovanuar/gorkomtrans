package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.Status;
import com.epam.anuar.gorkomtrans.service.ContractService;
import com.epam.anuar.gorkomtrans.service.ServiceException;
import com.epam.anuar.gorkomtrans.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.ResourceBundle;

public class SubmitContractAction implements Action {
    private static Logger log = LoggerFactory.getLogger(SubmitContractAction.class.getName());

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Validator.checkUnlogged(req);
        Contract contract = (Contract) req.getSession(false).getAttribute("contract");
        ContractService contractService = new ContractService();
        try {
            contractService.updateContractStatus(contract.getId(), Status.SUBMITTED);
        } catch (ServiceException e) {
            log.warn("Services error: " + e.toString());
            throw new ActionException();
        }
        ResourceBundle bundle = ResourceBundle.getBundle("other-text", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
        req.setAttribute("statusMessage", bundle.getString("status.submitted"));
        return new ActionResult("contract-status");
    }
}
