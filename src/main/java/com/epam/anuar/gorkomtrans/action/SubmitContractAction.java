package com.epam.anuar.gorkomtrans.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.anuar.gorkomtrans.Service.*;

public class SubmitContractAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = req.getParameter("tech-address");
        return submitContract(address, req);
    }
}
