package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.GarbageTechSpecification;
import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.service.ContractService;
import com.epam.anuar.gorkomtrans.service.ServiceException;
import com.epam.anuar.gorkomtrans.service.TechSpecService;
import com.epam.anuar.gorkomtrans.validator.Validator;
import com.epam.anuar.gorkomtrans.validator.Violation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class CreateContractAction implements Action {
    private final static Logger log = LoggerFactory.getLogger(CreateContractAction.class);
    String attributeName;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Validator.checkUnlogged(req);
        int count = 0;
        int euro = 0;
        int standard = 0;
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
                count++;
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

        Map<String, String[]> tempParameterMap = req.getParameterMap();
        Map<String, String> parameterMap = new LinkedHashMap<>();
        for (Map.Entry<String, String[]> entry : tempParameterMap.entrySet()) {
            parameterMap.put(entry.getKey(), entry.getValue()[0]);
            if (entry.getKey().contains("euro")) {
                euro = 1;
            }
            if (entry.getKey().contains("standard")) {
                standard = 1;
            }
        }

        Set<Violation> tempViolations = Validator.validateTechSpec(parameterMap, req);
        if (!tempViolations.isEmpty()) {
            List<String> violations = new ArrayList<>();
            for (int i = 0; i < 11; i++) {
                violations.add("");
            }
            for (Violation violation : tempViolations) {
                violations.set(violation.getFieldNumber(), violation.getViolation());
            }
            req.setAttribute("euro", euro);
            req.setAttribute("standard", standard);
            req.setAttribute("nonStandardNumber", count);
            req.setAttribute("listError", violations);
            req.setAttribute("paramValues", tempParameterMap);
            return new ActionResult("tech-spec");
        }

        try {
            TechSpecService techSpecService = new TechSpecService();
            Map<String, List<String>> garbageParameters = techSpecService.createGarbageContainerParameters(euroNumber, standardNumber, parameters);
            GarbageTechSpecification techSpecification = techSpecService.getNewTechSpec(address, garbageParameters, perMonth);

            ContractService contractService = new ContractService();
            User user = (User) req.getSession(false).getAttribute("user");
            Contract contract = contractService.getNewContract(user, techSpecification, providingMonthNumber);
            req.getSession(false).setAttribute("contract", contract);
            req.getSession(false).setAttribute("status", 0);
        } catch (ServiceException e) {
            log.warn("Services error: " + e.toString());
            throw new ActionException();
        }
        return new ActionResult("contract", true);
    }
}
