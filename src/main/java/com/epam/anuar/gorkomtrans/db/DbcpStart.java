package com.epam.anuar.gorkomtrans.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.epam.anuar.gorkomtrans.db.ConnectionPool.*;

public class DbcpStart {
    private static Logger log = LoggerFactory.getLogger(DbcpStart.class.getName());

    public static String start(ConnectionPool pool) {
        Connection con;
        Statement st = null;
        ResultSet rs = null;
        StringBuilder result = new StringBuilder();
        try {

            con = pool.takeConnection();

            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM STUDENTS");

            while (rs.next()) {
                result.append(rs.getInt(1))
                        .append(" ")
                        .append(rs.getString(2))
                        .append(" ")
                        .append(rs.getInt(3))
                        .append("\t");
                log.debug(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3));
            }

            pool.releaseConnection(con);
        } catch (SQLException e) {
            log.warn("SQLException", e);
            throw new RuntimeException();
        } finally{
            try {
                if (st != null) st.close();
                if (rs != null) rs.close();
            } catch (SQLException e1) {
                log.warn("Statement and ResultSet can't be closed.", e1);

            }

        }

        return result.toString();
    }
}
