package com.epam.anuar.gorkomtrans.service;

import com.epam.anuar.gorkomtrans.dao.ContractDao;
import com.epam.anuar.gorkomtrans.dao.DaoException;
import com.epam.anuar.gorkomtrans.dao.DaoFactory;
import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.GarbageTechSpecification;
import com.epam.anuar.gorkomtrans.entity.Status;
import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.util.ViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.epam.anuar.gorkomtrans.util.IdGenerator.generateID;

public class ContractService {
    public static final int USER_CONTRACT_PAGE = 1;
    public static final int USER_CONTRACT_RECORDS = 12;
    public static final int ALL_CONTRACTS_PAGE = 1;
    public static final int ALL_CONTRACTS_RECORDS = 12;
    private static final Comparator<Contract> CONTRACT_ID_COMPARATOR = (o1, o2) -> o1.getId().compareTo(o2.getId());
    private final static Logger log = LoggerFactory.getLogger(ContractService.class);
    private DaoFactory dao;
    private ContractDao contractDao;

    public ContractService() {
        dao = DaoFactory.getInstance();
        contractDao = dao.getContractDao();
    }

    public Contract getNewContract(User user, GarbageTechSpecification techSpecification, String providingMonthNumber) throws ServiceException {
        Integer id = generateID(contractDao);
        Contract contract = new Contract(id, user, techSpecification, Integer.parseInt(providingMonthNumber));
        try {
            contractDao.insert(contract);
        } catch (DaoException e) {
            log.warn("Can't insert new contract");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        return contract;
    }

    public Contract getContractById(String id) throws ServiceException {
        Contract contract;
        try {
            contract = contractDao.findById(Integer.parseInt(id));
        } catch (DaoException e) {
            log.warn("Can't find contract by id");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        return contract;
    }

    public List<Contract> getUserContactsPerPage(User user, int page, int recordsPerPage) throws ServiceException {
        List<Contract> contracts;
        try {
            contracts = contractDao.findByUserId(user.getId(), (page - 1) * recordsPerPage, recordsPerPage);
        } catch (DaoException e) {
            log.warn("Can't find contract by user id");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        Collections.sort(contracts, CONTRACT_ID_COMPARATOR);
        return contracts;
    }

    public List<Contract> getContractsByUserId(String id) throws ServiceException {
        List<Contract> contracts;
        try {
            contracts = contractDao.findByUserId(Integer.parseInt(id));
        } catch (DaoException e) {
            log.warn("Can't find contract by user id");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        return contracts;
    }

    public List<Contract> getUserContractByTechSpec(List<GarbageTechSpecification> techSpecs, String userId, int page, int recordsPerPage) throws ServiceException {
        List<Contract> contracts;
        try {
            contracts = contractDao.searchByAddress(techSpecs, userId, (page - 1) * recordsPerPage, recordsPerPage);
        } catch (DaoException e) {
            log.warn("Can't search contract by address");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        Collections.sort(contracts, CONTRACT_ID_COMPARATOR);
        return contracts;
    }

    public List<Contract> getContractsByTechSpec(List<GarbageTechSpecification> techSpecs, int page, int recordsPerPage) throws ServiceException {
        List<Contract> contracts;
        try {
            contracts = contractDao.searchByAddress(techSpecs, (page - 1) * recordsPerPage, recordsPerPage);
        } catch (DaoException e) {
            log.warn("Can't search contract by address");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        Collections.sort(contracts, CONTRACT_ID_COMPARATOR);
        return contracts;
    }

    public List<Contract> getAllContactsPerPage(int page, int recordsPerPage) throws ServiceException {
        List<Contract> contracts;
        try {
            contracts = contractDao.findAll((page - 1) * recordsPerPage, recordsPerPage);
        } catch (DaoException e) {
            log.warn("Can't find all contracts");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        Collections.sort(contracts, CONTRACT_ID_COMPARATOR);
        return contracts;
    }

    public void contractPay(String amount, String customerWalletId, String providerWalletId) throws ViolationException {
        try {
            contractDao.transfer(amount, customerWalletId, providerWalletId);
            log.info("Transaction successfully done");
        } catch (DaoException e) {
            log.info("Transaction failed");
            throw new ViolationException("Transaction failed");
        } finally {
            dao.close();
        }
    }

    public void deleteByUserId(String id) throws ServiceException {
        try {
            contractDao.deleteByUserId(id);
        } catch (DaoException e) {
            log.warn("Can't delete contract by user id");
            throw new ServiceException();
        } finally {
            dao.close();
        }
    }

    public void updateContractStatus(Integer id, Status status) throws ServiceException {
        try {
            contractDao.updateStatus(id , status);
        } catch (DaoException e) {
            log.warn("Can't update status of the contract");
            throw new ServiceException();
        } finally {
            dao.close();
        }
    }

    public void updateDateAndStatus(Integer id, String signDate, Status status) throws ServiceException {
        try {
            contractDao.update(id, signDate, status);
        } catch (DaoException e) {
            log.warn("Can't update sign date and status of the contract");
            throw new ServiceException();
        } finally {
            dao.close();
        }
    }

    public void deleteById(String id) throws ServiceException {
        try {
            contractDao.deleteById(id);
        } catch (DaoException e) {
            log.warn("Can't delete contract by id");
            throw new ServiceException();
        } finally {
            dao.close();
        }
    }
}
