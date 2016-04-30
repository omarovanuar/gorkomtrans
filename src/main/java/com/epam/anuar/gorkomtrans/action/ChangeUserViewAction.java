package com.epam.anuar.gorkomtrans.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import static com.epam.anuar.gorkomtrans.service.UserService.changeUserView;

public class ChangeUserViewAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String role = req.getParameter("role-select");
        String balance = req.getParameter("balance");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("###.##", symbols);
        balance = df.format(Double.parseDouble(balance));
        return  changeUserView(id, password, email, role, balance, req);
    }
}
