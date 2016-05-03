package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.service.ContractService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchAllAddressAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        int page = 1;
        int recordsPerPage = 13;
        String addressPart = req.getParameter("address-search");
        return ContractService.allAddressSearch(addressPart, page, recordsPerPage, req);
    }
}
