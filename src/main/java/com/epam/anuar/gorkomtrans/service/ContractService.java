package com.epam.anuar.gorkomtrans.service;

import com.epam.anuar.gorkomtrans.action.ActionResult;
import com.epam.anuar.gorkomtrans.dao.*;
import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.GarbageTechSpecification;
import com.epam.anuar.gorkomtrans.entity.Status;
import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.util.Validator;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.epam.anuar.gorkomtrans.util.IdGenerator.generateID;

public class ContractService {
    private static DaoFactory dao = DaoFactory.getInstance();
    private static ResourceBundle bundle;

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

    public static ActionResult submitContract(HttpServletRequest req) {
        Validator.checkUnlogged(req);
        ContractDao contractDao = dao.getContractDao();
        contractDao.updateStatus(((Contract) req.getSession(false).getAttribute("contract")).getId(), Status.SUBMITTED);
        dao.close();
        bundle = ResourceBundle.getBundle("other-text", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
        req.setAttribute("statusMessage", bundle.getString("status.submitted"));
        return new ActionResult("contract-status");
    }

    public static ActionResult viewContract(String id, HttpServletRequest req) {
        Validator.checkUnlogged(req);
        ContractDao contractDao = dao.getContractDao();
        Contract contract = contractDao.findById(Integer.parseInt(id));
        req.getSession(false).setAttribute("contract", contract);
        dao.close();
        if (contract.getStatus().equals(Status.NEW)) req.setAttribute("status", 0);
        else if (contract.getStatus().equals(Status.SUBMITTED)) {
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
        List<Contract> contracts = contractDao.findByUserId(user.getId(), (page - 1) * recordsPerPage, recordsPerPage);
        int noOfRecords = contractDao.userRowsNumber(user.getId().toString());
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (noOfPages == 0) noOfPages = 1;
        Collections.sort(contracts, Service.CONTRACT_ID_COMPARATOR);
        req.setAttribute("contracts", contracts);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        dao.close();
        return new ActionResult("contracts");
    }

    public static ActionResult showAllContracts(int page, int recordsPerPage, HttpServletRequest req) {
        Validator.checkAdminOrModer(req);
        ContractDao contractDao = dao.getContractDao();
        List<Contract> contracts = contractDao.findAll((page - 1) * recordsPerPage, recordsPerPage);
        int noOfRecords = contractDao.allRowsNumber();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (noOfPages == 0) noOfPages = 1;
        Collections.sort(contracts, Service.CONTRACT_ID_COMPARATOR);
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
        bundle = ResourceBundle.getBundle("other-text", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
        if (transaction.transfer(summa, userId, providerId) == 1) {
            contractDao.update(((Contract) req.getSession(false).getAttribute("contract")).getId(), DateTime.now().toString("dd.MM.YYYY HH:mm"), Status.AGREED);
            dao.close();
            req.setAttribute("statusMessage", bundle.getString("status.agreed"));
            return new ActionResult("contract-status");
        } else {
            dao.close();
            req.setAttribute("statusMessage", bundle.getString("status.agree-error"));
            return new ActionResult("contract-status");
        }
    }

    public static ActionResult denyContract(HttpServletRequest req) {
        Validator.checkAdminOrModer(req);
        ContractDao contractDao = dao.getContractDao();
        contractDao.updateStatus(((Contract) req.getSession(false).getAttribute("contract")).getId(), Status.DENIED);
        dao.close();
        bundle = ResourceBundle.getBundle("other-text", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
        req.setAttribute("statusMessage", bundle.getString("status.denied"));
        return new ActionResult("contract-status");
    }

    public static void deleteContract(String id) {
        ContractDao contractDao = dao.getContractDao();
        TechSpecDao techSpecDao = dao.getTechSpecDao();
        Contract contract = contractDao.findById(Integer.parseInt(id));
        String techSpecId = contract.getGarbageTechSpecification().getId().toString();
        techSpecDao.deleteById(techSpecId);
        contractDao.deleteById(id);
        dao.close();
    }

    public static ActionResult addressSearch(String addressPart, String userId, Integer page, Integer recordsPerPage, HttpServletRequest req) {
        Validator.checkUnlogged(req);
        ContractDao contractDao = dao.getContractDao();
        TechSpecDao techSpecDao = dao.getTechSpecDao();
        List<GarbageTechSpecification> techSpecs = techSpecDao.searchByAddress(addressPart);
        if (techSpecs.size() == 0) {
            req.setAttribute("noOfPages", 1);
            req.setAttribute("currentPage", 1);
            req.setAttribute("searchValue", addressPart);
            dao.close();
            return new ActionResult("contracts");
        }
        List<Contract> contracts = contractDao.searchByAddress(techSpecs, userId, (page - 1) * recordsPerPage, recordsPerPage);
        int noOfRecords = contracts.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (noOfPages == 0) noOfPages = 1;
        Collections.sort(contracts, Service.CONTRACT_ID_COMPARATOR);
        req.setAttribute("contracts", contracts);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("searchValue", addressPart);
        dao.close();
        return new ActionResult("contracts");
    }

    public static ActionResult allAddressSearch(String addressPart, Integer page, Integer recordsPerPage, HttpServletRequest req) {
        Validator.checkUnlogged(req);
        ContractDao contractDao = dao.getContractDao();
        TechSpecDao techSpecDao = dao.getTechSpecDao();
        List<GarbageTechSpecification> techSpecs = techSpecDao.searchByAddress(addressPart);
        if (techSpecs.size() == 0) {
            req.setAttribute("noOfPages", 1);
            req.setAttribute("currentPage", 1);
            req.setAttribute("searchValue", addressPart);
            dao.close();
            return new ActionResult("contract-sanction");
        }
        List<Contract> contracts = contractDao.searchByAddress(techSpecs, (page - 1) * recordsPerPage, recordsPerPage);
        int noOfRecords = contracts.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (noOfPages == 0) noOfPages = 1;
        Collections.sort(contracts, Service.CONTRACT_ID_COMPARATOR);
        req.setAttribute("allContracts", contracts);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("searchValue", addressPart);
        dao.close();
        return new ActionResult("contract-sanction");
    }
}
