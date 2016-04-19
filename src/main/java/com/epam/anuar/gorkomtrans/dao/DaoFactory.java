package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.ContextListener;
import com.epam.anuar.gorkomtrans.db.ConnectionPool;
import com.epam.anuar.gorkomtrans.entity.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServlet;
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

    public void beginTransaction() {
        try {
            this.con.setAutoCommit(false);
        } catch (SQLException e) {
            log.warn("can't set autocommit", e);
            abortTransaction();
        }
    }

    public void endTransaction() {
        try {
            this.con.commit();
        } catch (SQLException e) {
            log.warn("can't commit changes");
            abortTransaction();
        }
    }

    public void abortTransaction() {
        try {
            this.con.rollback();
        } catch (SQLException e) {
            log.warn("connection can't be rollbacked", e);
            this.con = null;
        }
    }

    private void checkConnection() {
        try {
            if (this.con == null ||this.con.isClosed()) {
                this.open();
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    public void getConnectionQueue() {
        pool.getConnectionQueue();
    }
}
