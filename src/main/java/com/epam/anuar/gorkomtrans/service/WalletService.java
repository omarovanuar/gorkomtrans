package com.epam.anuar.gorkomtrans.service;

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

    public Wallet addNewWallet(String account) {
        Integer walletId = generateID(walletDao);
        walletDao.insert(walletId, account);
        Wallet wallet = new Wallet(walletId, account, Money.parse("KZT 0.00"));
        dao.close();
        return wallet;
    }

    public void removeWallet(String account) {
        walletDao.deleteByAccount(account);
        dao.close();
    }

    public void deleteById(String id) {
        walletDao.deleteById(id);
        dao.close();
    }

    public void updateWallet(String id, String balance) {
        walletDao.updateBalance(id, balance);
        dao.close();
    }
}
