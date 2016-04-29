package com.epam.anuar.gorkomtrans.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoService {
    private static Logger log = LoggerFactory.getLogger(DaoService.class.getName());

    protected static PreparedStatement getStatement(Connection con, String value, List<String> parameters) {
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

    protected static byte executeStatement(Connection con, String value, List<String> parameters) {
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

    protected static int calculateRowNumber(String value, Connection con) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int noOfRecords = 0;
        try {
            ps = con.prepareStatement(value);
            rs = ps.executeQuery();
            while (rs.next()) noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            log.warn("Statement can't be executed");
            throw new DaoException();
        } finally {
            DaoService.closeResultSet(rs);
            DaoService.closeStatement(ps);
        }
        return noOfRecords;
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
}
