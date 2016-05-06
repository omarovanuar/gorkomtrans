package com.epam.anuar.gorkomtrans.service;

import com.epam.anuar.gorkomtrans.dao.DaoException;
import com.epam.anuar.gorkomtrans.dao.DaoFactory;
import com.epam.anuar.gorkomtrans.dao.WalletDao;
import com.epam.anuar.gorkomtrans.entity.Wallet;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.anuar.gorkomtrans.util.IdGenerator.generateID;

public class WalletService {
    private final static Logger log = LoggerFactory.getLogger(WalletService.class);
    private DaoFactory dao;
    private WalletDao walletDao;

    public WalletService() {
        dao = DaoFactory.getInstance();
        walletDao = dao.getWalletDao();
    }

    public Wallet addNewWallet(String account) throws ServiceException {
        Integer walletId = generateID(walletDao);
        try {
            walletDao.insert(walletId, account);
        } catch (DaoException e) {
            log.warn("Can't insert new wallet");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        Wallet wallet = new Wallet(walletId, account, Money.parse("KZT 0.00"));
        return wallet;
    }

    public void removeWallet(String account) throws ServiceException {
        try {
            walletDao.deleteByAccount(account);
        } catch (DaoException e) {
            log.warn("Can't delete wallet by account");
            throw new ServiceException();
        } finally {
            dao.close();
        }
    }

    public void deleteById(String id) throws ServiceException {
        try {
            walletDao.deleteById(id);
        } catch (DaoException e) {
            log.warn("Can't delete wallet by id");
            throw new ServiceException();
        } finally {
            dao.close();
        }
    }

    public void updateWallet(String id, String balance) throws ServiceException {
        try {
            walletDao.updateBalance(id, balance);
        } catch (DaoException e) {
            log.warn("Can't update balance of the wallet");
            throw new ServiceException();
        } finally {
            dao.close();
        }
    }
}
