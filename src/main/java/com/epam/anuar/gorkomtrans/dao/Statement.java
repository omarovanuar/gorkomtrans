package com.epam.anuar.gorkomtrans.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Statement {
    private static Logger log = LoggerFactory.getLogger(Statement.class.getName());

    public static PreparedStatement getStatement(Connection con, String value, List<String> parameters) throws DaoException {
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(value);
            for (int i = 0; i < parameters.size(); i++) {
                ps.setString(i + 1, parameters.get(i));
            }
            ps.executeQuery();
        } catch (SQLException e) {
            log.warn("Statement can't be executed");
            throw new DaoException();
        }
        return ps;
    }

    public static byte executeStatement(Connection con, String value, List<String> parameters) throws DaoException {
        PreparedStatement ps = null;
        byte isExecuted = 4;
        try {
            ps = con.prepareStatement(value);
            for (int i = 0; i < parameters.size(); i++) {
                ps.setString(i + 1, parameters.get(i));
            }
            ps.execute();
            isExecuted = 0;
        } catch (SQLException e) {
            log.warn("Statement can't be executed");
            throw new DaoException();
        } finally {
            closeStatement(ps);
        }
        return isExecuted;
    }

    public static void closeStatement(PreparedStatement ps) throws DaoException {
        try {
            if (ps != null) ps.close();
        } catch (SQLException e) {
            log.warn("Statement can't be closed");
            throw new DaoException();
        }
    }

    public static void closeResultSet(ResultSet rs) throws DaoException {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e1) {
            log.warn("ResultSet can't be closed");
            throw new DaoException();
        }
    }
}
