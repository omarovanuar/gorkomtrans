package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.service.ContractService;
import com.epam.anuar.gorkomtrans.service.TechSpecService;
import com.epam.anuar.gorkomtrans.service.UserService;
import com.epam.anuar.gorkomtrans.service.WalletService;
import com.epam.anuar.gorkomtrans.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteUserAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Validator.checkAdmin(req);
        String id = req.getParameter("current-user");
        UserService userService = new UserService();
        ContractService contractService = new ContractService();
        TechSpecService techSpecService = new TechSpecService();
        User user = userService.getUserById(id);
        String walletId = user.getWallet().getId().toString();
        List<Contract> contracts = contractService.getContractsByUserId(id);
        for (Contract contract : contracts) {
            techSpecService.deleteTechSpecById(contract.getGarbageTechSpecification().getId().toString());
        }
        contractService.deleteByUserId(id);
        new WalletService().deleteById(walletId);
        userService.deleteUserById(id);
        return new ShowAllUsersAction().execute(req, resp);
    }
}
