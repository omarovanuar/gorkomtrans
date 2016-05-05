package com.epam.anuar.gorkomtrans.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ConnectionPool {
    private static Logger log = LoggerFactory.getLogger(ConnectionPool.class.getName());
    public static final String PROPERTIES_FILE = "connection";
    private static final String DEFAULT_DRIVER = "org.h2.Driver";
    public static final int DEFAULT_POOL_SIZE = 10;
    private static ConnectionPool instance;
    private BlockingQueue<Connection> connectionQueue;
    private static Map<String, String> connectionParameters;

    public static void init() throws SQLException {
        if (instance == null) {
            readConnectionProperties();
            int poolSize;
            try {
                poolSize = Integer.parseInt(connectionParameters.get("poolSize"));
                if (poolSize < 1) {
                    poolSize = DEFAULT_POOL_SIZE;
                }
            } catch (NumberFormatException e) {
                log.warn("Not valid size of the pool", e);
                poolSize = DEFAULT_POOL_SIZE;
            }
            try {
                log.debug("Trying to create pool of connections...");
                instance = new ConnectionPool(
                        connectionParameters.get("driver"),
                        connectionParameters.get("url"),
                        connectionParameters.get("user"),
                        connectionParameters.get("password"), poolSize);
                log.debug("Connection pool succesfully initialized");
            } catch (ClassNotFoundException e) {
                log.info("Driver " + connectionParameters.get("driver") + " not found.");
                try {
                    instance = new ConnectionPool(DEFAULT_DRIVER,
                            connectionParameters.get("url"),
                            connectionParameters.get("user"),
                            connectionParameters.get("password"), poolSize);
                    log.info("org.h2.Driver used as default");
                } catch (ClassNotFoundException e1) {
                    log.error("Default driver not found.", e1);
                    throw new RuntimeException();
                }
            }
        }
    }

    public static void dispose() throws SQLException, ClassNotFoundException {
        if (instance != null) {
            instance.clearConnectionQueue();
            instance = null;
            log.debug("Connection pool succesfully disposed");
            org.h2.Driver.unload();
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPool(String driver, String url, String user, String password, int poolSize) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            Connection connection = DriverManager.getConnection(url, user, password);
            connectionQueue.offer(connection);
        }

    }

    public Connection takeConnection() throws SQLException {

        Connection connection;
        try {
            connection = connectionQueue.poll(30, SECONDS);
        } catch (InterruptedException e) {
            log.warn("Free connection waiting interrupted.", e);
            throw new RuntimeException();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            if (!connection.isClosed()) {
                if (!connectionQueue.offer(connection)) {
                    log.warn("Connection not added. Possible `leakage` of connections");
                }
            } else {
                connectionQueue.offer(newConnection());
                log.info("Created new connection instead of closed one");
            }
        } catch (SQLException e) {
            log.warn("SQLException at connection isClosed() checking. Connection not added", e);
        }
    }

    private static void readConnectionProperties() {
        ResourceBundle rb = ResourceBundle.getBundle(PROPERTIES_FILE);
        connectionParameters = new HashMap<>();
        connectionParameters.put("driver", rb.getString("db.driver"));
        connectionParameters.put("url", rb.getString("db.url"));
        connectionParameters.put("user", rb.getString("db.user"));
        connectionParameters.put("password", rb.getString("db.password"));
        connectionParameters.put("poolSize", rb.getString("db.poolSize"));
    }

    private Connection newConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(
                    connectionParameters.get("url"),
                    connectionParameters.get("user"),
                    connectionParameters.get("password"));
        } catch (SQLException e) {
            log.warn("New connection can't be created");
            throw new RuntimeException();
        }
        return connection;
    }

    private void clearConnectionQueue() throws SQLException {
        Connection connection;
        while ((connection = connectionQueue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            connection.close();
        }
    }
}





