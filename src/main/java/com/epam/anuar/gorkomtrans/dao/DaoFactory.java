package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.connection.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory {
    private static Logger log = LoggerFactory.getLogger(DaoFactory.class.getName());
    private Connection con;
    private static final DaoFactory instance = new DaoFactory();
    private static ConnectionPool pool;

    private DaoFactory() {
        pool = ConnectionPool.getInstance();
    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public void open() {
        try {
            this.con = pool.takeConnection();
        } catch (SQLException e) {
            log.warn("Connection from connection pool can't be taken");
            throw new RuntimeException();
        }
    }

    public void close() {
        checkConnection();
        pool.releaseConnection(this.con);
        this.con = null;
    }

    public UserDao getUserDao() {
        checkConnection();
        return new UserDao(this.con);
    }

    public TechSpecDao getTechSpecDao() {
        checkConnection();
        return new TechSpecDao(this.con);
    }

    public ContractDao getContractDao() {
        checkConnection();
        return new ContractDao(this.con);
    }

    public WalletDao getWalletDao() {
        checkConnection();
        return new WalletDao(this.con);
    }

    private void checkConnection() {
        try {
            if (this.con == null || this.con.isClosed()) {
                this.open();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
