package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.User;
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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class ChangeUserViewAction implements Action {
    private final static Logger log = LoggerFactory.getLogger(ChangeUserViewAction.class);
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Validator.checkAdmin(req);
        Map<String, String> parameters = new LinkedHashMap<>();
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            parameters.put(entry.getKey(), entry.getValue()[0]);
        }
        UserService userService = new UserService();
        User user;

        try {
            user = userService.getUserById(parameters.get("id"));
            try {
                Set<Violation> tempViolations = Validator.validateUserView(parameters, req);
                if (!tempViolations.isEmpty()) {
                    List<String> violations = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        violations.add("");
                    }
                    for (Violation violation : tempViolations) {
                        violations.set(violation.getFieldNumber(), violation.getViolation());
                    }
                    req.setAttribute("violations", violations);
                    req.setAttribute("userParam", user);
                    return new ActionResult("user-view");
                }
            } catch (ViolationException e) {
                log.warn("Can't validate parameters");
                throw new ActionException();
            }
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("###.##", symbols);
            String balance = df.format(Double.parseDouble(parameters.get("balance")));
            userService.updateUserView(parameters);
            WalletService walletService = new WalletService();
            walletService.updateWallet(user.getWallet().getId().toString(), balance);
            user = userService.getUserById(parameters.get("id"));
        } catch (ServiceException e) {
            log.warn("Services error: " + e.toString());
            throw new ActionException();
        }
        req.setAttribute("userParam", user);
        return new ActionResult("user-view");
    }
}
