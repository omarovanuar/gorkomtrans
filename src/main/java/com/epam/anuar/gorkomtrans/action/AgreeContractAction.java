package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.Status;
import com.epam.anuar.gorkomtrans.service.ContractService;
import com.epam.anuar.gorkomtrans.service.ServiceException;
import com.epam.anuar.gorkomtrans.service.UserService;
import com.epam.anuar.gorkomtrans.util.Validator;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.ResourceBundle;

public class AgreeContractAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Validator.checkAdminOrModer(req);
        Contract contract = (Contract) req.getSession(false).getAttribute("contract");
        UserService userService = new UserService();
        ContractService contractService = new ContractService();
        String amount = contract.getContractAmount().toString();
        String customerWalletId = contract.getUser().getWallet().getId().toString();
        String providerWalletId = userService.getUserByLogin("admin").getWallet().getId().toString();
        ResourceBundle bundle = ResourceBundle.getBundle("other-text", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
        try {
            contractService.contractPay(amount, customerWalletId, providerWalletId);
        } catch (ServiceException e) {
            req.setAttribute("statusMessage", bundle.getString("status.agree-error"));
            return new ActionResult("contract-status");
        }
        contractService.updateDateAndStatus(contract.getId(), DateTime.now().toString("dd.MM.YYYY HH:mm"), Status.AGREED);
        req.setAttribute("statusMessage", bundle.getString("status.agreed"));
        return new ActionResult("contract-status");
    }
}
