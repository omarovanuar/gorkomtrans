package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.validator.Validator;
import com.epam.anuar.gorkomtrans.validator.Violation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FillTechSpecAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Validator.checkUnlogged(req);
        String nonStandardNumber = req.getParameter("non-standard-type-number");
        String euro = req.getParameter("euro-type");
        String standard = req.getParameter("standard-type");
        Violation emptyTechSpec = Validator.isEmptyTechSpec(euro, standard, nonStandardNumber, req);
        if (emptyTechSpec != null) {
            req.setAttribute("violation", emptyTechSpec.getViolation());
            return new ActionResult("services");
        }
        if (euro == null) euro = "0";
        if (standard == null) standard = "0";
        req.setAttribute("euro", euro);
        req.setAttribute("standard", standard);
        req.setAttribute("nonStandardNumber", nonStandardNumber);
        return new ActionResult("tech-spec");
    }




}
