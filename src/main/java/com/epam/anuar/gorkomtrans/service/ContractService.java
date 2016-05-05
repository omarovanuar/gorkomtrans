package com.epam.anuar.gorkomtrans.service;

import com.epam.anuar.gorkomtrans.dao.ContractDao;
import com.epam.anuar.gorkomtrans.dao.DaoException;
import com.epam.anuar.gorkomtrans.dao.DaoFactory;
import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.GarbageTechSpecification;
import com.epam.anuar.gorkomtrans.entity.Status;
import com.epam.anuar.gorkomtrans.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

import static com.epam.anuar.gorkomtrans.util.IdGenerator.generateID;

public class ContractService {
    private final static Logger log = LoggerFactory.getLogger(ContractService.class);
    private DaoFactory dao;
    private ContractDao contractDao;

    public ContractService() {
        dao = DaoFactory.getInstance();
        contractDao = dao.getContractDao();
    }

    public Contract getNewContract(User user, GarbageTechSpecification techSpecification, String providingMonthNumber) {
        Integer id = generateID(contractDao);
        Contract contract = new Contract(id, user, techSpecification, Integer.parseInt(providingMonthNumber));
        contractDao.insert(contract);
        dao.close();
        return contract;
    }

    public Contract getContractById(String id) {
        Contract contract = contractDao.findById(Integer.parseInt(id));
        dao.close();
        return contract;
    }

    public List<Contract> getUserContactsPerPage(User user, int page, int recordsPerPage) {
        List<Contract> contracts = contractDao.findByUserId(user.getId(), (page - 1) * recordsPerPage, recordsPerPage);
        Collections.sort(contracts, Service.CONTRACT_ID_COMPARATOR);
        dao.close();
        return contracts;
    }

    public List<Contract> getContractsByUserId(String id) {
        List<Contract> contracts = contractDao.findByUserId(Integer.parseInt(id));
        dao.close();
        return contracts;
    }

    public List<Contract> getUserContractByTechSpec(List<GarbageTechSpecification> techSpecs, String userId, int page, int recordsPerPage) {
        List<Contract> contracts = contractDao.searchByAddress(techSpecs, userId, (page - 1) * recordsPerPage, recordsPerPage);
        Collections.sort(contracts, Service.CONTRACT_ID_COMPARATOR);
        dao.close();
        return contracts;
    }

    public List<Contract> getContractsByTechSpec(List<GarbageTechSpecification> techSpecs, int page, int recordsPerPage) {
        List<Contract> contracts = contractDao.searchByAddress(techSpecs, (page - 1) * recordsPerPage, recordsPerPage);
        Collections.sort(contracts, Service.CONTRACT_ID_COMPARATOR);
        dao.close();
        return contracts;
    }

    public List<Contract> getAllContactsPerPage(int page, int recordsPerPage) {
        List<Contract> contracts = contractDao.findAll((page - 1) * recordsPerPage, recordsPerPage);
        Collections.sort(contracts, Service.CONTRACT_ID_COMPARATOR);
        dao.close();
        return contracts;
    }

    public void contractPay(String amount, String customerWalletId, String providerWalletId) throws ServiceException {
        try {
            contractDao.transfer(amount, customerWalletId, providerWalletId);
            log.info("Transaction successfully done");
        } catch (DaoException e) {
            log.info("Transaction failed");
            throw new ServiceException("Transaction failed");
        }
        dao.close();
    }

    public void deleteByUserId(String id) {
        contractDao.deleteByUserId(id);
        dao.close();
    }

    public void updateContractStatus(Integer id, Status status) {
        contractDao.updateStatus(id , status);
        dao.close();
    }

    public void updateDateAndStatus(Integer id, String signDate, Status status) {
        contractDao.update(id, signDate, status);
        dao.close();
    }

    public void deleteById(String id) {
        contractDao.deleteById(id);
        dao.close();
    }
}
