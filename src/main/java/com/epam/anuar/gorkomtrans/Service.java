package com.epam.anuar.gorkomtrans;

import com.epam.anuar.gorkomtrans.action.ActionResult;
import com.epam.anuar.gorkomtrans.dao.ContractDao;
import com.epam.anuar.gorkomtrans.dao.DaoFactory;
import com.epam.anuar.gorkomtrans.dao.TechSpecDao;
import com.epam.anuar.gorkomtrans.dao.UserDao;
import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.GarbageContainerType;
import com.epam.anuar.gorkomtrans.entity.GarbageTechSpecification;
import com.epam.anuar.gorkomtrans.entity.User;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class Service {
    public static ActionResult checkUser(String login, String password, HttpServletRequest req) {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        ContractDao contractDao = DaoFactory.getInstance().getContractDao();
        //todo salty hash
        User user = userDao.findByCredentials(login, password);
        if (user != null) {
            req.getSession(false).setAttribute("contracts", contractDao.findByUserId(user.getId()));
            req.getSession().setAttribute("user", user);
            return new ActionResult("home", true);
        } else {
            req.setAttribute("loginError", "Incorrect login or password");
            return new ActionResult("welcome");
        }
    }

    public static ActionResult registerUser(String login, String password, String email, HttpServletRequest req) {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        switch (userDao.insert(login, password, email)) {
            case 0:
                return new ActionResult("", true);
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

    public static ActionResult update(String id, String password, String email, String firstName, String lastName, String phoneNumber,
                                      String mainAddress, String bank, String bankAccount, HttpServletRequest req) {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        ContractDao contractDao = DaoFactory.getInstance().getContractDao();
        switch (userDao.update(id, password, email,firstName, lastName, phoneNumber, mainAddress, bank, bankAccount)) {
            case 0:
                req.getSession(false).setAttribute("contracts", contractDao.findByUserId(Integer.parseInt(id)));
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
        if (euro == null) euro = "0";
        if (standard == null) standard = "0";
        req.getSession(false).setAttribute("euro", euro);
        req.getSession(false).setAttribute("standard", standard);
        req.getSession(false).setAttribute("nonStandardNumber", nonStandardNumber);
        return new ActionResult("tech-spec", true);
    }

    public static ActionResult createContract(User user, GarbageTechSpecification techSpecification, String providingMonthNumber, HttpServletRequest req) {
        ContractDao contractDao = DaoFactory.getInstance().getContractDao();
        Integer id = generateContractId(contractDao);
        Contract contract = new Contract(id, user, techSpecification, Integer.parseInt(providingMonthNumber));
        req.getSession(false).setAttribute("contract", contract);
        contractDao.insert(contract);
        return new ActionResult("contract", true);
    }

    public static ActionResult sanctionContract(String contractCost, Contract contract, HttpServletRequest req) {
        ContractDao contractDao = DaoFactory.getInstance().getContractDao();
        contractDao.update(((Contract) req.getSession(false).getAttribute("contract")).getId(), DateTime.now().toString("dd.MM.YYYY HH:mm"));
        req.getSession(false).setAttribute("isSubmitted", "Contract successfully submitted");
        return new ActionResult("submitted-contract", true);
    }

    public static GarbageTechSpecification createTechSpec(String address, String euroNumber, String standardNumber,
                                                          List<String> parameters, String perMonth, HttpServletRequest req) {
        TechSpecDao techSpecDao = DaoFactory.getInstance().getTechSpecDao();
        Integer id = generateTechSpecId(techSpecDao);
        Map<String, List<String>> garbageParameters = createGarbageContainerParameters(euroNumber, standardNumber, parameters);
        GarbageTechSpecification techSpecification = new GarbageTechSpecification(id, address, garbageParameters, Integer.parseInt(perMonth));

        techSpecDao.insert(techSpecification);
        return techSpecification;
    }

    private static Map<String, List<String>> createGarbageContainerParameters(String euroNumber, String standardNumber, List<String> parameters) {
        Map<String, List<String>> techSpecParameters = new HashMap<>();
        GarbageContainerType tempType = GarbageContainerType.EURO;
        techSpecParameters = fillMapParameters(tempType, tempType.toString(), euroNumber, tempType.getContainerCapacity().toString(), techSpecParameters);
        tempType = GarbageContainerType.STANDARD;
        techSpecParameters = fillMapParameters(tempType, tempType.toString(), standardNumber, tempType.getContainerCapacity().toString(), techSpecParameters);
        for (Integer i = 0; i < parameters.size(); i += 2) {
            tempType = GarbageContainerType.NON_STANDARD;
            techSpecParameters = fillMapParameters(tempType, tempType.toString() + i.toString(), parameters.get(i), parameters.get(i+1), techSpecParameters);
        }
        return techSpecParameters;
    }

    private static Map<String, List<String>> fillMapParameters(GarbageContainerType type, String typeString, String containerNumber, String containerCapacity,  Map<String, List<String>> techSpecParameters) {
        List<String> numberAndCapacity = new ArrayList<>();
        numberAndCapacity.add(containerNumber);
        numberAndCapacity.add(containerCapacity);
        techSpecParameters.put(typeString, numberAndCapacity);
        return techSpecParameters;
    }

    private static Integer generateTechSpecId(TechSpecDao techSpecDao) {
        Random random = new Random();
        Integer id = random.nextInt(10000) + 10000;
        if (techSpecDao.findById(id) != null) {
            id = generateTechSpecId(techSpecDao);
        }
        return id;
    }

    private static Integer generateContractId(ContractDao contractDao) {
        Random random = new Random();
        Integer id = random.nextInt(10000) + 20000;
        if (contractDao.findById(id) != null) {
            id = generateContractId(contractDao);
        }
        return id;
    }


    public static ActionResult contractView(String id, HttpServletRequest req) {
        ContractDao contractDao = DaoFactory.getInstance().getContractDao();
        req.getSession(false).setAttribute("contract", contractDao.findById(Integer.parseInt(id)));
        return new ActionResult("contract");
    }
}
