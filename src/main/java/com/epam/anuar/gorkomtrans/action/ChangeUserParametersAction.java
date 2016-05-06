package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.entity.Wallet;
import com.epam.anuar.gorkomtrans.service.ServiceException;
import com.epam.anuar.gorkomtrans.service.UserService;
import com.epam.anuar.gorkomtrans.service.WalletService;
import com.epam.anuar.gorkomtrans.util.Validator;
import com.epam.anuar.gorkomtrans.util.Violation;
import com.epam.anuar.gorkomtrans.util.ViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ChangeUserParametersAction implements Action {
    private final static Logger log = LoggerFactory.getLogger(ChangeUserParametersAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Validator.checkUnlogged(req);
        Map<String, String> parameters = new LinkedHashMap<>();
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            parameters.put(entry.getKey(), entry.getValue()[0]);
        }
        String id = parameters.remove("id");

        try {
            Set<Violation> tempViolations = Validator.validatePersonalCabinet(parameters, req);
            if (!tempViolations.isEmpty()) {
                ActionFunctions.violationView(tempViolations, parameters, req);
                return new ActionResult("personal-cabinet");
            }
        } catch (ViolationException e) {
            log.warn("Can't validate parameters");
            throw new ActionException();
        }
        User currentUser = (User) req.getSession(false).getAttribute("user");
        User updatedUser;
        UserService userService = new UserService();
        WalletService walletService = new WalletService();

        try {
            if (!parameters.get("Bank").equalsIgnoreCase(currentUser.getBankName()) ||
                    !parameters.get("BankAccount").equalsIgnoreCase(currentUser.getBankAccount())) {
                walletService.removeWallet(currentUser.getBankRequisitions());
                Wallet wallet = walletService.addNewWallet(currentUser.getBankRequisitions());
                updatedUser = userService.getUserWithNewWallet(id, parameters, wallet.getId().toString());
            } else {
                updatedUser = userService.getUpdatedUser(id, parameters, currentUser.getWallet());
            }
        } catch (ServiceException e) {
            log.warn("Services error: " + e.toString());
            throw new ActionException();
        }
        updatedUser.setRoleByCode(currentUser.getRoleByCode());
        req.getSession(false).setAttribute("user", updatedUser);
        return new ActionResult("personal-cabinet", true);
    }
}
