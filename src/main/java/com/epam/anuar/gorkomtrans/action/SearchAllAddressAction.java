package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.GarbageTechSpecification;
import com.epam.anuar.gorkomtrans.service.ContractService;
import com.epam.anuar.gorkomtrans.service.TechSpecService;
import com.epam.anuar.gorkomtrans.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SearchAllAddressAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Validator.checkUnlogged(req);
        int page = ContractService.ALL_CONTRACTS_PAGE;
        int recordsPerPage = ContractService.ALL_CONTRACTS_RECORDS;
        String addressPart = req.getParameter("address-search");
        List<GarbageTechSpecification> techSpecs = new TechSpecService().getTechSpecByAddressPart(addressPart);
        if (techSpecs.size() == 0) {
            req.setAttribute("noOfPages", 1);
            req.setAttribute("currentPage", 1);
            req.setAttribute("searchValue", addressPart);
            return new ActionResult("contract-sanction");
        }
        List<Contract> contracts = new ContractService().getContractsByTechSpec(techSpecs, page, recordsPerPage);
        int noOfRecords = contracts.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (noOfPages == 0) noOfPages = 1;
        req.setAttribute("allContracts", contracts);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("searchValue", addressPart);
        return new ActionResult("contract-sanction");
    }
}
