package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.service.ContractService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchAddressAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        int page = 1;
        int recordsPerPage = 13;
        String addressPart = req.getParameter("address-search");
        User user = (User) req.getSession(false).getAttribute("user");
        String id = user.getId().toString();
        return ContractService.addressSearch(addressPart, id, page, recordsPerPage, req);
    }
}
