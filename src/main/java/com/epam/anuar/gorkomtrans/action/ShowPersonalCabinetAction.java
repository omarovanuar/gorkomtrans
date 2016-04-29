package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ShowPersonalCabinetAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        List<String> userParamList = new ArrayList<>();
        userParamList.add("Password");
        userParamList.add("Email");
        userParamList.add("FirstName");
        userParamList.add("LastName");
        userParamList.add("PhoneNumber");
        userParamList.add("MainAddress");
        userParamList.add("Bank");
        userParamList.add("BankAccount");
        List<String> violations = new ArrayList<>();
        for (int i = 0; i < userParamList.size(); i++) {
            violations.add(i, "");
        }
        List<String> userParamName = ActionService.getRegisterParameterNames(req);
        userParamName.remove(0);
        List<String> values = new ArrayList<>();
        User user = (User) req.getSession(false).getAttribute("user");
        values.add(user.getPassword());
        values.add(user.getEmail());
        values.add(user.getFirstName());
        values.add(user.getLastName());
        values.add(user.getPhoneNumber());
        values.add(user.getMainAddress());
        values.add(user.getBankName());
        values.add(user.getBankAccount());

        return ActionService.showPersonalCabinet(req, userParamList, userParamName, violations, values);
    }
}
