package com.epam.anuar.gorkomtrans.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.epam.anuar.gorkomtrans.action.ShowPageAttributesAction.showRegister;
import static com.epam.anuar.gorkomtrans.action.ActionFunctions.getRegisterParameterNames;

public class ShowRegisterAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        List<String> userParamList = new ArrayList<>();
        userParamList.add("Login");
        userParamList.add("Password");
        userParamList.add("Email");
        userParamList.add("FirstName");
        userParamList.add("LastName");
        userParamList.add("PhoneNumber");
        userParamList.add("MainAddress");
        userParamList.add("Bank");
        userParamList.add("BankAccount");
        List<String> userParamName = getRegisterParameterNames(req);
        List<String> violations = new ArrayList<>();
        for (int i = 0; i < userParamList.size(); i++) {
            violations.add(i, "");
        }
        List<String> values = new ArrayList<>();
        for (int i = 0; i < userParamList.size(); i++) {
            values.add(i, "");
        }
        return showRegister(req, userParamList, userParamName, violations, values);
    }


}
