package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.Wallet;
import com.epam.anuar.gorkomtrans.service.UserService;
import com.epam.anuar.gorkomtrans.service.WalletService;
import com.epam.anuar.gorkomtrans.util.Validator;
import com.epam.anuar.gorkomtrans.util.Violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class RegisterAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String[]> parameterMap = req.getParameterMap();
        Map<String, String> parameters = new LinkedHashMap<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            parameters.put(entry.getKey(), entry.getValue()[0]);
        }
        Set<Violation> tempViolations = Validator.validateRegister(parameters, req);
        if (!tempViolations.isEmpty()) {
            ActionFunctions.violationView(tempViolations, parameters, req);
            return new ActionResult("register");
        }
        WalletService walletService = new WalletService();
        Wallet wallet = walletService.addNewWallet(parameters.get("Bank") + " " + parameters.get("BankAccount"));
        parameters.put("WalletId", wallet.getId().toString());
        UserService userService = new UserService();
        userService.addNewUser(parameters);
        return new ActionResult("welcome", true);
    }


}
