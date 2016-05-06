package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.service.*;
import com.epam.anuar.gorkomtrans.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteUserAction implements Action {
    private final static Logger log = LoggerFactory.getLogger(DeleteUserAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Validator.checkAdmin(req);
        String id = req.getParameter("current-user");
        UserService userService = new UserService();
        ContractService contractService = new ContractService();
        TechSpecService techSpecService = new TechSpecService();

        try {
            User user = userService.getUserById(id);
            String walletId = user.getWallet().getId().toString();
            List<Contract> contracts = contractService.getContractsByUserId(id);
            for (Contract contract : contracts) {
                techSpecService.deleteTechSpecById(contract.getGarbageTechSpecification().getId().toString());
            }
            contractService.deleteByUserId(id);
            new WalletService().deleteById(walletId);
            userService.deleteUserById(id);
        } catch (ServiceException e) {
            log.warn("Services error: " + e.toString());
            throw new ActionException();
        }
        return new ShowAllUsersAction().execute(req, resp);
    }
}
