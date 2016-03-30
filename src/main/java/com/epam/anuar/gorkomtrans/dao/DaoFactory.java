package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.db.ConnectionPool;

import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoFactory {
    Connection con;

    private DaoFactory(ConnectionPool pool) {
        try {
            con = pool.takeConnection();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static DaoFactory newInstance(ConnectionPool pool) {
        return new DaoFactory(pool);
    }

    public UserDao createUserDao() {
        UserDao dao = new UserDao(con);
        return dao;
    }


}
