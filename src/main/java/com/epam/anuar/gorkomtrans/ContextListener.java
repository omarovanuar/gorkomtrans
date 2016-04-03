package com.epam.anuar.gorkomtrans;

import com.epam.anuar.gorkomtrans.db.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

public class ContextListener implements ServletContextListener {
    ConnectionPool pool;
    private static Logger log = LoggerFactory.getLogger(ContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ConnectionPool.init();
            pool = ConnectionPool.getInstance();
            sce.getServletContext().setAttribute("db.pool", pool);
        } catch (SQLException e) {
            log.error("Connection pool can't be created");
            throw new RuntimeException();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.dispose();
        } catch (SQLException e) {
            log.error("Connection pool can't be disposed");
            throw new RuntimeException();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
