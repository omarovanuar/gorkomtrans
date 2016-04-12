package com.epam.anuar.gorkomtrans.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ObjectStreamClass;

import static com.epam.anuar.gorkomtrans.Service.fillTechSpec;

public class FillTechSpecAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String nonStandardNumber = req.getParameter("non-standard-type-number");
        String euro = req.getParameter("euro-type");
        String standard = req.getParameter("standard-type");
        return fillTechSpec(euro, standard, nonStandardNumber, req);
    }




}
