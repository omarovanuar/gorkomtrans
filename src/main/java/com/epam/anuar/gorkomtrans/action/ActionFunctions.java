package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.service.ContractService;
import com.epam.anuar.gorkomtrans.service.ServiceException;
import com.epam.anuar.gorkomtrans.service.TechSpecService;
import com.epam.anuar.gorkomtrans.validator.Violation;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ActionFunctions {

    protected static List<String> getRegisterParameterNames(HttpServletRequest req) {
        ResourceBundle bundle = ResourceBundle.getBundle("login-register", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
        List<String> userParamName = new ArrayList<>();
        userParamName.add(bundle.getString("login.login"));
        userParamName.add(bundle.getString("login.password"));
        userParamName.add(bundle.getString("login.email"));
        userParamName.add(bundle.getString("login.first-name"));
        userParamName.add(bundle.getString("login.last-name"));
        userParamName.add(bundle.getString("login.phone-number"));
        userParamName.add(bundle.getString("login.main-address"));
        userParamName.add(bundle.getString("login.bank"));
        userParamName.add(bundle.getString("login.bank-account"));
        return userParamName;
    }

    protected static void violationView(Set<Violation> tempViolations, Map<String, String> parameters, HttpServletRequest req) {
        List<String> violations = new ArrayList<>();
        for (int i = 0; i < parameters.size(); i++) {
            violations.add("");
        }
        for (Violation violation : tempViolations) {
            violations.set(violation.getFieldNumber(), violation.getViolation());
        }
        req.setAttribute("violations", violations);
        List<String> list = new ArrayList<>(parameters.keySet());
        req.setAttribute("userParamList", list);
        List<String> values = new ArrayList<>(parameters.values());
        req.setAttribute("values", values);
        List<String> names = getRegisterParameterNames(req);
        if (req.getPathInfo().equals("/personal-cabinet")) {
            names.remove(0);
        }
        req.setAttribute("userParamName", names);
    }

    protected static void deleteContract(String id) throws ServiceException {
        ContractService contractService = new ContractService();
        TechSpecService techSpecService = new TechSpecService();
        Contract contract = contractService.getContractById(id);
        String techSpecId = contract.getGarbageTechSpecification().getId().toString();
        techSpecService.deleteTechSpecById(techSpecId);
        contractService.deleteById(id);
    }

    protected static Map<String, String> getParameterMap(HttpServletRequest req) {
        Map<String, String[]> parameterMap = req.getParameterMap();
        Map<String, String> parameters = new LinkedHashMap<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            parameters.put(entry.getKey(), entry.getValue()[0]);
        }
        return parameters;
    }
}
