package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.GarbageTechSpecification;
import com.epam.anuar.gorkomtrans.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static com.epam.anuar.gorkomtrans.action.ActionService.*;

public class CreateContractAction implements Action {
    String attributeName;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {

        List<String> parameters = new ArrayList<>();
        String address = req.getParameter("tech-address");
        String euroNumber = req.getParameter("euro");
        String standardNumber = req.getParameter("standard");
        String perMonth = req.getParameter("perMonth");
        String providingMonthNumber = req.getParameter("providingMonthNumber");
        for (int i = 1; i < 4; i++) {
            attributeName = "container-number-" + i;
            if (req.getParameter(attributeName) != null)
            {
                parameters.add(req.getParameter(attributeName));
            } else {
                parameters.add("0");
            }

            attributeName = "container-capacity-" + i;
            if (req.getParameter(attributeName) != null)
            {
                parameters.add(req.getParameter(attributeName));
            } else {
                parameters.add("0");
            }
        }

        if (euroNumber == null) euroNumber = "0";
        if (standardNumber == null) standardNumber = "0";

        User user = (User) req.getSession(false).getAttribute("user");
        GarbageTechSpecification techSpecification = createTechSpec(address, euroNumber, standardNumber, parameters, perMonth, req);
        return createContract(user, techSpecification, providingMonthNumber, req);
    }
}
