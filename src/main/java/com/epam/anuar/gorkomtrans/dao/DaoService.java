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

    protected static byte executeStatement(Connection con, String value) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        byte isExecuted = 3;
        try {
            ps = con.prepareStatement(value);
            ps.execute();
            isExecuted = 0;
        } catch (SQLException e) {
            log.warn("Statement can't be executed");
            throw new DaoException();
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
        }
        return isExecuted;
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
        if (users.size() != 0) {
            StringBuilder view = new StringBuilder();
            for (User user : users) {
                view.append(user.getLogin())
                        .append(" ")
                        .append(user.getEmail())
                        .append("\t");
            }
            return view.toString();
        } else {
            return "No users found";
        }
    }

    public static String viewUser(User user) {
        if (user != null) {
            StringBuilder view = new StringBuilder();
            view.append(user.getLogin())
                    .append(" ")
                    .append(user.getEmail())
                    .append("\t");
            return view.toString();
        } else {
            return "User wasn't found";
        }
    }
}
