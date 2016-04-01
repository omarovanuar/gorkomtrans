package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.db.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory {
    private static Logger log = LoggerFactory.getLogger(DaoFactory.class.getName());
    private Connection con;
    private static DaoFactory instance = new DaoFactory();

    private DaoFactory() {
        try {
            con = ConnectionPool.getInstance().takeConnection();
        } catch (SQLException e) {
            log.warn("Connection from connection pool can't be taken");
            throw new RuntimeException();
        }
    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        UserDao userDao = new UserDao(con);
        return userDao;
    }


}
