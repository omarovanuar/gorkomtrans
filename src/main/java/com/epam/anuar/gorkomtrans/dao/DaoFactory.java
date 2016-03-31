package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.db.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory {
    private static Logger log = LoggerFactory.getLogger(DaoFactory.class.getName());
    private Connection con;

    private DaoFactory() {
        try {
            con = ConnectionPool.getInstance().takeConnection();
        } catch (SQLException e) {
            log.warn("Connection from connection pool can't be taken");
            throw new RuntimeException();
        }
    }

    public static DaoFactory newInstance() {
        return new DaoFactory();
    }

    public UserDao createUserDao() {
        UserDao dao = new UserDao(con);
        return dao;
    }


}
