package com.epam.anuar.gorkomtrans.service;

import com.epam.anuar.gorkomtrans.action.ActionResult;
import com.epam.anuar.gorkomtrans.dao.*;
import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.util.IdGenerator;
import com.epam.anuar.gorkomtrans.util.Validator;
import com.epam.anuar.gorkomtrans.util.Violation;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import static com.epam.anuar.gorkomtrans.util.IdGenerator.generateID;

public class UserService {
    private static DaoFactory dao = DaoFactory.getInstance();

    public static ActionResult checkUser(String login, String password, HttpServletRequest req) {
        UserDao userDao = dao.getUserDao();
        User user = userDao.findByCredentials(login, password);
        if (user != null) {
            req.getSession().setAttribute("user", user);
            dao.close();
            return new ActionResult("", true);
        } else {
            if (req.getSession(false).getAttribute("locale").equals("ru")) {
                req.setAttribute("loginError", "Не правильный логин или пароль");
            } else {
                req.setAttribute("loginError", "Invalid login or password");
            }
            return new ActionResult("welcome");
        }
    }

    public static ActionResult registerUser(Map<String, String> parameters, HttpServletRequest req) {
        Set<Violation> tempViolations = Validator.validateRegister(parameters, req);
        if (!tempViolations.isEmpty()) {
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
            req.setAttribute("userParamName", names);
            return new ActionResult("register");
        }
        UserDao userDao = dao.getUserDao();
        parameters.put("Id", generateID(userDao).toString());
        WalletDao walletDao = dao.getWalletDao();
        Integer walletId = generateID(walletDao);
        walletDao.insert(walletId, parameters.get("Bank") + " " + parameters.get("BankAccount"));
        parameters.put("WalletId", walletId.toString());
        userDao.insertByParameters(parameters);
        dao.close();
        return new ActionResult("welcome", true);
    }

    public static ActionResult changeUserParameters(String id, Map<String, String> parameters, HttpServletRequest req) {
        Set<Violation> tempViolations = Validator.validatePersonalCabinet(parameters, req);
        if (!tempViolations.isEmpty()) {
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
            List<String> values = getCurrentUserParameters(req);
            req.setAttribute("values", values);
            List<String> name = getRegisterParameterNames(req);
            name.remove(0);
            req.setAttribute("userParamName", name);
            return new ActionResult("personal-cabinet");
        }
        Validator.checkUnlogged(req);
        UserDao userDao = dao.getUserDao();
        if (!parameters.get("Bank").equalsIgnoreCase(getCurrentUserParameters(req).get(6)) ||
                !parameters.get("BankAccount").equalsIgnoreCase(getCurrentUserParameters(req).get(7))) {
            WalletDao walletDao = dao.getWalletDao();
            Integer walletId = IdGenerator.generateID(walletDao);
            walletDao.insert(walletId, parameters.get("Bank") + " " + parameters.get("BankAccount"));
            walletDao.deleteById(userDao.findById(Integer.parseInt(id)).getWallet().getId().toString());
            userDao.updateWithWallet(id, parameters, walletId.toString());
        } else {
            userDao.update(id, parameters);
        }
        dao.close();
        req.getSession(false).setAttribute("user", userDao.findById(Integer.parseInt(id)));
        return new ActionResult("personal-cabinet", true);
    }

    public static ActionResult showAllUsers(int page, int recordsPerPage, HttpServletRequest req) {
        Validator.checkAdmin(req);
        UserDao userDao = dao.getUserDao();
        List<User> users = userDao.findAll((page - 1) * recordsPerPage, recordsPerPage);
        int noOfRecords = userDao.allRowsNumber();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (noOfPages == 0) noOfPages = 1;
        Collections.sort(users, Service.USER_ID_COMPARATOR);
        req.setAttribute("allUsers", users);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        dao.close();
        return new ActionResult("admin-cabinet");
    }

    public static ActionResult viewUser(String id, HttpServletRequest req) {
        Validator.checkAdmin(req);
        UserDao userDao = dao.getUserDao();
        User user = userDao.findById(Integer.parseInt(id));
        req.setAttribute("userParam", user);
        dao.close();
        return new ActionResult("user-view");
    }

    public static ActionResult changeUserView(String id, String password, String email, String role, String balance, HttpServletRequest req) {
        Validator.checkAdmin(req);
        UserDao userDao = dao.getUserDao();
        User user = userDao.findById(Integer.parseInt(id));
        if (Validator.validateDouble(balance, req) != null) {
            req.setAttribute("updateUserParamError", "Invalid balance");
            req.setAttribute("userParam", userDao.findById(Integer.parseInt(id)));
            dao.close();
            return new ActionResult("user-view");
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("###.##", symbols);
        balance = df.format(Double.parseDouble(balance));
        switch (userDao.updatePassEmailRole(id, password, email, role)) {
            case 0:
                WalletDao walletDao = dao.getWalletDao();
                if (walletDao.updateBalance(user.getWallet().getId().toString(), balance) > 0) {
                    req.setAttribute("updateUserParamError", "Invalid balance");
                }
                req.setAttribute("userParam", userDao.findById(Integer.parseInt(id)));
                dao.close();
                return new ActionResult("user-view");
            case 2:
                req.setAttribute("updateUserParamError", "Current email is already exist");
                req.setAttribute("userParam", user);
                dao.close();
                return new ActionResult("user-view");
            case 3:
                req.setAttribute("updateUserParamError", "Please, fill all fields");
                req.setAttribute("userParam", user);
                dao.close();
                return new ActionResult("user-view");
            default:
                req.setAttribute("error", "Unknown error");
                return new ActionResult("error-page");
        }

    }

    public static ActionResult deleteUser(String id, HttpServletRequest req) {
        Validator.checkAdmin(req);
        UserDao userDao = dao.getUserDao();
        WalletDao walletDao = dao.getWalletDao();
        ContractDao contractDao = dao.getContractDao();
        TechSpecDao techSpecDao = dao.getTechSpecDao();
        User user = userDao.findById(Integer.parseInt(id));
        String walletId = user.getWallet().getId().toString();
        List<Contract> contracts = contractDao.findByUserId(Integer.parseInt(id));
        for (Contract contract : contracts) {
            techSpecDao.deleteById(contract.getGarbageTechSpecification().getId().toString());
        }
        contractDao.deleteByUserId(id);
        walletDao.deleteById(walletId);
        userDao.deleteById(id);
        dao.close();
        return showAllUsers(1, 13, req);
    }

    public static List<String> getCurrentUserParameters(HttpServletRequest req) {
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
        return values;
    }

    public static List<String> getRegisterParameterNames(HttpServletRequest req) {
        List<String> userParamName = new ArrayList<>();
        if (req.getSession(false).getAttribute("locale").equals("ru")) {
            userParamName.add("Логин");
            userParamName.add("Пароль");
            userParamName.add("Email");
            userParamName.add("Имя");
            userParamName.add("Фамилия");
            userParamName.add("Номер телефона");
            userParamName.add("Основной адрес");
            userParamName.add("Банк");
            userParamName.add("Банковский счет");
        } else {
            userParamName.add("Login");
            userParamName.add("Password");
            userParamName.add("Email");
            userParamName.add("FirstName");
            userParamName.add("LastName");
            userParamName.add("PhoneNumber");
            userParamName.add("MainAddress");
            userParamName.add("Bank");
            userParamName.add("BankAccount");
        }
        return userParamName;
    }

    public static ActionResult searchByLogin(String loginPart, Integer page, Integer recordsPerPage, HttpServletRequest req) {
        Validator.checkAdmin(req);
        UserDao userDao = dao.getUserDao();
        List<User> users = userDao.searchByLogin(loginPart, (page - 1) * recordsPerPage, recordsPerPage);
        if (users.size() == 0) {
            req.setAttribute("noOfPages", 1);
            req.setAttribute("currentPage", 1);
            req.setAttribute("searchValue", loginPart);
            dao.close();
            return new ActionResult("admin-cabinet");
        }
        int noOfRecords = users.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (noOfPages == 0) noOfPages = 1;
        Collections.sort(users, Service.USER_ID_COMPARATOR);
        req.setAttribute("allUsers", users);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("searchValue", loginPart);
        dao.close();
        return new ActionResult("admin-cabinet");
    }
}
