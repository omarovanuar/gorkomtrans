package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoService {
    private static Logger log = LoggerFactory.getLogger(DaoService.class.getName());

    protected static PreparedStatement getStatement(Connection con, String value) {
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(value);
            ps.execute();
        } catch (SQLException e) {
            log.warn("Statement can't be executed");
            throw new DaoException();
        }
        return ps;
    }

    protected static void executeStatement(Connection con, String value) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(value);
            ps.execute();
        } catch (SQLException e) {
            log.warn("Statement can't be executed");
            throw new DaoException();
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
        }
    }

    protected static void closeStatement(PreparedStatement ps) {
        try {
            if (ps != null) ps.close();
        } catch (SQLException e) {
            log.warn("Statement can't be closed");
            throw new DaoException();
        }
    }

    protected static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e1) {
            log.warn("ResultSet can't be closed");
            throw new DaoException();
        }
    }

    public static String viewAllUsers(List<User> users) {
        StringBuilder allUsers = new StringBuilder();
        for (User user : users) {
            allUsers.append(user.getLogin())
                    .append(" ")
                    .append(user.getEmail())
                    .append("\t");
        }
        return allUsers.toString();
    }
}
