package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserContractAction implements Action {
    private final static Logger log = LoggerFactory.getLogger(DeleteUserContractAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String id = req.getParameter("current-contract");
        try {
            ActionFunctions.deleteContract(id);
        } catch (ServiceException e) {
            log.warn("Services error: " + e.toString());
            throw new ActionException();
        }
        return new ShowAllContractsAction().execute(req, resp);
    }
}
