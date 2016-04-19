package com.epam.anuar.gorkomtrans;

import com.epam.anuar.gorkomtrans.db.ConnectionPool;
import com.epam.anuar.gorkomtrans.entity.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContextListener implements ServletContextListener {
    private List<String> userParamList;
    private static Logger log = LoggerFactory.getLogger(ContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        userParamList = new ArrayList<>();
        userParamList.add("Login");
        userParamList.add("Password");
        userParamList.add("Email");
        userParamList.add("First-name");
        userParamList.add("Last-name");
        userParamList.add("Phone-number");
        userParamList.add("Main-address");
        userParamList.add("Bank");
        userParamList.add("Bank-account");
        sce.getServletContext().setAttribute("userParamList", userParamList);
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
            e.printStackTrace();
        }
    }
}
