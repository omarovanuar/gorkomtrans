package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.Wallet;
import com.epam.anuar.gorkomtrans.service.ServiceException;
import com.epam.anuar.gorkomtrans.service.UserService;
import com.epam.anuar.gorkomtrans.service.WalletService;
import com.epam.anuar.gorkomtrans.validator.Validator;
import com.epam.anuar.gorkomtrans.validator.Violation;
import com.epam.anuar.gorkomtrans.validator.ViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

public class RegisterAction implements Action {
    private final static Logger log = LoggerFactory.getLogger(RegisterAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Map<String, String> parameters = ActionFunctions.getParameterMap(req);
        try {
            Set<Violation> tempViolations = Validator.validateRegister(parameters, req);
            if (!tempViolations.isEmpty()) {
                ActionFunctions.violationView(tempViolations, parameters, req);
                return new ActionResult("register");
            }
        } catch (ViolationException e) {
            log.warn("Can't validate parameters");
            throw new ActionException();
        }
        try {
            WalletService walletService = new WalletService();
            Wallet wallet = walletService.addNewWallet(parameters.get("Bank") + " " + parameters.get("BankAccount"));
            parameters.put("WalletId", wallet.getId().toString());
            UserService userService = new UserService();
            userService.addNewUser(parameters);
        } catch (ServiceException e) {
            log.warn("Services error: " + e.toString());
            throw new ActionException();
        }
        return new ActionResult("welcome", true);
    }


}
