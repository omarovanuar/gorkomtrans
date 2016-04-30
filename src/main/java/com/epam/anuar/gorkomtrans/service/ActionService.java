package com.epam.anuar.gorkomtrans.service;

import com.epam.anuar.gorkomtrans.action.ActionResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ActionService {

    public static ActionResult showRegister(HttpServletRequest req, List<String> userParamList, List<String> userParamName, List<String> violations, List<String> values) {
        req.setAttribute("userParamList", userParamList);
        req.setAttribute("userParamName", userParamName);
        req.setAttribute("violations", violations);
        req.setAttribute("values", values);

        return new ActionResult("register");
    }

    public static ActionResult showPersonalCabinet(HttpServletRequest req, List<String> userParamList, List<String> userParamName, List<String> violations, List<String> values) {
        req.setAttribute("userParamList", userParamList);
        req.setAttribute("userParamName", userParamName);
        req.setAttribute("violations", violations);
        req.setAttribute("values", values);

        return new ActionResult("personal-cabinet");
    }


}
