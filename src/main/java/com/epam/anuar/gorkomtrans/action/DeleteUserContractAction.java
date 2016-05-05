package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.service.ContractService;
import com.epam.anuar.gorkomtrans.service.TechSpecService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserContractAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("current-contract");
        ContractService contractService = new ContractService();
        TechSpecService techSpecService = new TechSpecService();
        Contract contract = contractService.getContractById(id);
        String techSpecId = contract.getGarbageTechSpecification().getId().toString();
        techSpecService.deleteTechSpecById(techSpecId);
        contractService.deleteById(id);
        return new ShowAllContractsAction().execute(req, resp);
    }
}
