package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.service.ContractService;
import com.epam.anuar.gorkomtrans.service.ServiceException;
import com.epam.anuar.gorkomtrans.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAllContractsAction implements Action {
    private final static Logger log = LoggerFactory.getLogger(ShowAllContractsAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Validator.checkAdminOrModer(req);
        int page = ContractService.ALL_CONTRACTS_PAGE;
        int recordsPerPage = ContractService.ALL_CONTRACTS_RECORDS;
        if(req.getParameter("page") != null) page = Integer.parseInt(req.getParameter("page"));

        ContractService contractService = new ContractService();
        List<Contract> contracts;
        try {
            contracts = contractService.getAllContactsPerPage(page, recordsPerPage);
        } catch (ServiceException e) {
            log.warn("Services error: " + e.toString());
            throw new ActionException();
        }
        int noOfRecords = contracts.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (noOfPages == 0) noOfPages = 1;
        req.setAttribute("allContracts", contracts);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        return new ActionResult("contract-sanction");
    }
}
