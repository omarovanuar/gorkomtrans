package com.epam.anuar.gorkomtrans;

import com.epam.anuar.gorkomtrans.db.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Runner {
    private static Logger log = LoggerFactory.getLogger(Runner.class.getName());

    public static void main(String[] args) {
        ConnectionPool pool;
        Connection con;
        Statement st = null;
        ResultSet rs = null;
        try {
            ConnectionPool.init();
            pool = ConnectionPool.getInstance();
            con = pool.takeConnection();

            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM STUDENTS");

            while (rs.next()) {
                log.debug(rs.getInt(1)
                        + " " + rs.getString(2) + " " + rs.getInt(3));
            }

            pool.releaseConnection(con);
            ConnectionPool.dispose();
        } catch (SQLException e) {
            log.warn("SQLException: ", e);
        } finally{
            try {
                if (st != null)st.close();
                if (rs != null) rs.close();
            } catch (SQLException e1) {
                log.warn("Statement and ResultSet can't be closed. ", e1);
            }
        }
    }

}
