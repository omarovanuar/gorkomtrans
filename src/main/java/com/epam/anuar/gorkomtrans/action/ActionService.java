package com.epam.anuar.gorkomtrans.action;

import com.epam.anuar.gorkomtrans.Service;
import com.epam.anuar.gorkomtrans.dao.*;
import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.GarbageTechSpecification;
import com.epam.anuar.gorkomtrans.entity.Status;
import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.util.Validator;
import com.epam.anuar.gorkomtrans.util.Violation;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.epam.anuar.gorkomtrans.util.IdGenerator.generateID;

public class ActionService {
    private static DaoFactory dao = DaoFactory.getInstance();

    public static ActionResult checkUser(String login, String password, HttpServletRequest req) {
        UserDao userDao = dao.getUserDao();
        //todo salty hash
        User user = userDao.findByCredentials(login, password);
        if (user != null) {
            req.getSession().setAttribute("user", user);
            dao.close();
            return new ActionResult("", true);
        } else {
            req.setAttribute("loginError", "Incorrect login or password");
            return new ActionResult("welcome");
        }
    }

    public static ActionResult registerUser(Map<String, String> parameters, HttpServletRequest req) {
        Set<Violation> tempViolations = Validator.validateRegister(parameters);
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
            return new ActionResult("personal-cabinet");
        }
        Validator.checkUnlogged(req);
        UserDao userDao = dao.getUserDao();
        userDao.update(id, parameters);
        dao.close();
        req.getSession(false).setAttribute("user", userDao.findById(Integer.parseInt(id)));
        return new ActionResult("personal-cabinet", true);
    }

    public static ActionResult fillTechSpec(String euro, String standard, String nonStandardNumber, HttpServletRequest req) {
        Validator.checkUnlogged(req);
        Validator.isEmptyTechSpec(euro, standard, nonStandardNumber);
        if (euro == null) euro = "0";
        if (standard == null) standard = "0";
        req.setAttribute("euro", euro);
        req.setAttribute("standard", standard);
        req.setAttribute("nonStandardNumber", nonStandardNumber);
        return new ActionResult("tech-spec");
    }

    public static ActionResult createContract(User user, GarbageTechSpecification techSpecification, String providingMonthNumber, HttpServletRequest req) {
        Validator.checkUnlogged(req);
        ContractDao contractDao = dao.getContractDao();
        Integer id = generateID(contractDao);
        Contract contract = new Contract(id, user, techSpecification, Integer.parseInt(providingMonthNumber));
        req.getSession(false).setAttribute("contract", contract);
        contractDao.insert(contract);
        req.getSession(false).setAttribute("status", 0);
        dao.close();
        return new ActionResult("contract", true);
    }

    public static ActionResult submitContract(Contract contract, HttpServletRequest req) {
        Validator.checkUnlogged(req);
        ContractDao contractDao = dao.getContractDao();
        contractDao.updateStatus(((Contract) req.getSession(false).getAttribute("contract")).getId(), Status.SUBMITTED);
        dao.close();
        req.setAttribute("statusMessage", "Contract successfully submitted");
        return new ActionResult("contract-status");
    }

    public static GarbageTechSpecification createTechSpec(String address, String euroNumber, String standardNumber,
                                                          List<String> parameters, String perMonth, HttpServletRequest req) {
        Validator.checkUnlogged(req);
        TechSpecDao techSpecDao = dao.getTechSpecDao();
        Integer id = generateID(techSpecDao);
        Map<String, List<String>> garbageParameters = Service.createGarbageContainerParameters(euroNumber, standardNumber, parameters);
        GarbageTechSpecification techSpecification = new GarbageTechSpecification(id, address, garbageParameters, Integer.parseInt(perMonth));
        techSpecDao.insert(techSpecification);
        dao.close();
        return techSpecification;
    }

    public static ActionResult viewContract(String id, HttpServletRequest req) {
        Validator.checkUnlogged(req);
        ContractDao contractDao = dao.getContractDao();
        Contract contract = contractDao.findById(Integer.parseInt(id));
        req.getSession(false).setAttribute("contract", contract);
        dao.close();
        if (contract.getStatus().equals(Status.NEW)) req.setAttribute("status", 0);
        else if (contract.getStatus().equals(Status.SUBMITTED)){
            req.setAttribute("status", 1);
        } else {
            req.setAttribute("status", 2);
        }
        return new ActionResult("contract");
    }


    public static ActionResult showUserContracts(int page, int recordsPerPage, HttpServletRequest req) {
        Validator.checkUnlogged(req);
        ContractDao contractDao = dao.getContractDao();
        User user = (User) req.getSession(false).getAttribute("user");
        List<Contract> contracts = contractDao.findByUserId(user.getId(), (page-1) * recordsPerPage, recordsPerPage);
        int noOfRecords = contractDao.userRowsNumber(user.getId().toString());
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (noOfPages == 0) noOfPages = 1;
        req.setAttribute("contracts", contracts);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        dao.close();
        return new ActionResult("contracts");
    }

    public static ActionResult showAllContracts(int page, int recordsPerPage, HttpServletRequest req) {
        Validator.checkAdminOrModer(req);
        ContractDao contractDao = dao.getContractDao();
        List<Contract> contracts = contractDao.findAll((page-1) * recordsPerPage, recordsPerPage);
        int noOfRecords = contractDao.allRowsNumber();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (noOfPages == 0) noOfPages = 1;
        req.setAttribute("allContracts", contracts);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        dao.close();
        return new ActionResult("contract-sanction");
    }

    public static ActionResult agreeContract(HttpServletRequest req) {
        Validator.checkAdminOrModer(req);
        UserDao userDao = dao.getUserDao();
        ContractDao contractDao = dao.getContractDao();
        ContractPayTransaction transaction = dao.getContractPayTransaction();
        String summa = ((Contract) req.getSession(false).getAttribute("contract")).getContractAmount().toString();
        String userId = ((Contract) req.getSession(false).getAttribute("contract")).getUser().getWallet().getId().toString();
        String providerId = userDao.findByLogin("admin").getWallet().getId().toString();
        if (transaction.transfer(summa, userId, providerId) == 1) {
            contractDao.update(((Contract) req.getSession(false).getAttribute("contract")).getId(), DateTime.now().toString("dd.MM.YYYY HH:mm"), Status.AGREED);
            dao.close();
            req.setAttribute("statusMessage", "Contract successfully agreed");
            return new ActionResult("contract-status");
        } else {
            dao.close();
            req.setAttribute("statusMessage", "Contract not agreed, try later");
            return new ActionResult("contract-status");
        }
    }

    public static ActionResult denyContract(HttpServletRequest req) {
        Validator.checkAdminOrModer(req);
        ContractDao contractDao = dao.getContractDao();
        contractDao.updateStatus(((Contract) req.getSession(false).getAttribute("contract")).getId(), Status.DENIED);
        dao.close();
        req.setAttribute("statusMessage", "Contract successfully denied");
        return new ActionResult("contract-status");
    }

    public static ActionResult showAllUsers(int page, int recordsPerPage, HttpServletRequest req) {
        Validator.checkAdmin(req);
        UserDao userDao = dao.getUserDao();
        List<User> users = userDao.findAll((page-1) * recordsPerPage, recordsPerPage);
        int noOfRecords = userDao.allRowsNumber();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (noOfPages == 0) noOfPages = 1;
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

    public static ActionResult showRegister(HttpServletRequest req, List<String> userParamList, List<String> violations, List<String> values) {
        req.setAttribute("userParamList", userParamList);
        req.setAttribute("violations", violations);
        req.setAttribute("values", values);

        return new ActionResult("register");
    }

    public static ActionResult showPersonalCabinet(HttpServletRequest req, List<String> userParamList, List<String> violations, List<String> values) {
        req.setAttribute("userParamList", userParamList);
        req.setAttribute("violations", violations);
        req.setAttribute("values", values);

        return new ActionResult("personal-cabinet");
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
}
