package com.epam.anuar.gorkomtrans.listener;

import com.epam.anuar.gorkomtrans.connection.ConnectionPool;
import com.epam.anuar.gorkomtrans.entity.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

public class ContextListener implements ServletContextListener {
    private static Logger log = LoggerFactory.getLogger(ContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Provider provider = Provider.getProviderInstance();
        sce.getServletContext().setAttribute("provider", provider);
        try {
            ConnectionPool.init();
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
            log.error(e.toString());
            throw new RuntimeException();
        }
    }
}
