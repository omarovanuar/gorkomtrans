package com.epam.anuar.gorkomtrans;

import com.epam.anuar.gorkomtrans.action.ActionResult;
import com.epam.anuar.gorkomtrans.dao.*;
import com.epam.anuar.gorkomtrans.entity.*;
import com.epam.anuar.gorkomtrans.util.Validator;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.epam.anuar.gorkomtrans.util.IdGenerator.generateID;

public class Service {
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

    public static ActionResult registerUser(List<String> parameters, HttpServletRequest req) {
        UserDao userDao = dao.getUserDao();
        parameters.add(0, generateID(userDao).toString());
        WalletDao walletDao = dao.getWalletDao();
        Integer walletId = generateID(walletDao);
        walletDao.insert(walletId, parameters.get(8) + " " + parameters.get(9));
        parameters.add(walletId.toString());
        byte result = userDao.insertByParameters(parameters);
        dao.close();
        switch (result) {
            case 0:
                return new ActionResult("welcome", true);
            case 1:
                req.setAttribute("registerError", "Current login is already exist");
                return new ActionResult("register");
            case 2:
                req.setAttribute("registerError", "Current email is already exist");
                return new ActionResult("register");
            case 3:
                req.setAttribute("registerError", "Please, fill all fields");
                return new ActionResult("register");
            default:
                req.setAttribute("registerError", "Invalid parameters");
                return new ActionResult("register");
        }
    }

    public static ActionResult changeUserParameters(String id, String password, String email, String firstName, String lastName, String phoneNumber,
                                                    String mainAddress, String bank, String bankAccount, HttpServletRequest req) {
        Validator.checkUnlogged(req);
        UserDao userDao = dao.getUserDao();
        byte result = userDao.update(id, password, email, firstName, lastName, phoneNumber, mainAddress, bank, bankAccount);
        dao.close();
        switch (result) {
            case 0:
                req.getSession(false).setAttribute("user", userDao.findById(Integer.parseInt(id)));
                return new ActionResult("personal-cabinet", true);
            case 2:
                req.setAttribute("updateUserError", "Current email is already exist");
                return new ActionResult("personal-cabinet");
            case 3:
                req.setAttribute("updateUserError", "Please, fill all fields");
                return new ActionResult("personal-cabinet");
            case 4:
                req.setAttribute("upsertCustomerError", "Please, fill all fields");
                return new ActionResult("personal-cabinet");
            default:
                req.setAttribute("updateUserError", "Invalid parameters");
                return new ActionResult("personal-cabinet");
        }
    }

    public static ActionResult fillTechSpec(String euro, String standard, String nonStandardNumber, HttpServletRequest req) {
        Validator.checkUnlogged(req);
        Validator.isEmptyTechSpec(euro, standard, nonStandardNumber);
        if (euro == null) euro = "0";
        if (standard == null) standard = "0";
        req.getSession(false).setAttribute("euro", euro);
        req.getSession(false).setAttribute("standard", standard);
        req.getSession(false).setAttribute("nonStandardNumber", nonStandardNumber);
        return new ActionResult("tech-spec", true);
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
        Map<String, List<String>> garbageParameters = createGarbageContainerParameters(euroNumber, standardNumber, parameters);
        GarbageTechSpecification techSpecification = new GarbageTechSpecification(id, address, garbageParameters, Integer.parseInt(perMonth));
        techSpecDao.insert(techSpecification);
        dao.close();
        return techSpecification;
    }

    private static Map<String, List<String>> createGarbageContainerParameters(String euroNumber, String standardNumber, List<String> parameters) {
        Map<String, List<String>> techSpecParameters = new HashMap<>();
        GarbageContainerType tempType = GarbageContainerType.EURO;
        techSpecParameters = fillMapParameters(tempType.toString(), euroNumber, tempType.getContainerCapacity().toString(), techSpecParameters);
        tempType = GarbageContainerType.STANDARD;
        techSpecParameters = fillMapParameters(tempType.toString(), standardNumber, tempType.getContainerCapacity().toString(), techSpecParameters);
        for (Integer i = 0; i < parameters.size(); i += 2) {
            tempType = GarbageContainerType.NON_STANDARD;
            techSpecParameters = fillMapParameters(tempType.toString() + i.toString(), parameters.get(i), parameters.get(i+1), techSpecParameters);
        }
        return techSpecParameters;
    }

    private static Map<String, List<String>> fillMapParameters(String typeString, String containerNumber, String containerCapacity,  Map<String, List<String>> techSpecParameters) {
        List<String> numberAndCapacity = new ArrayList<>();
        numberAndCapacity.add(containerNumber);
        numberAndCapacity.add(containerCapacity);
        techSpecParameters.put(typeString, numberAndCapacity);
        return techSpecParameters;
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

    public static ActionResult showRegister(HttpServletRequest req) {

        return new ActionResult("register");
    }
}
