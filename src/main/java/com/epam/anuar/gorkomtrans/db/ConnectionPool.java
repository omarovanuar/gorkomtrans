package com.epam.anuar.gorkomtrans.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static Logger log = LoggerFactory.getLogger(ConnectionPool.class.getName());
    public static final String PROPERTIES_FILE = "connection";
    private static final String DEFAULT_DRIVER = "org.h2.Driver";
    public static final int DEFAULT_POOL_SIZE = 10;
    /** single instance */
    private static ConnectionPool instance;
    /** free connections queue */
    private BlockingQueue<Connection> connectionQueue;

    public static void init () throws SQLException {
        if (instance == null) {
            ResourceBundle rb = ResourceBundle.getBundle(PROPERTIES_FILE);
            String driver = rb.getString("db.driver");
            String url = rb.getString("db.url");
            String user = rb.getString("db.user");
            String password = rb.getString("db.password");
            String poolSizeStr = rb.getString("db.poolsize");
            int poolSize;
            try {
                poolSize = Integer.parseInt(poolSizeStr);
                if (poolSize < 1){
                    poolSize = DEFAULT_POOL_SIZE;
                }
            } catch (NumberFormatException e) {
                log.warn("Not valid size of the pool", e);
                poolSize = DEFAULT_POOL_SIZE;
            }
            try {
                log.debug("Trying to create pool of connections...");
                instance = new ConnectionPool (driver, url, user, password, poolSize);
                log.debug("Connection pool succesfully initialized");
            } catch (ClassNotFoundException e) {
                log.info("Driver " + driver + " not found.");
                try {
                    instance = new ConnectionPool (DEFAULT_DRIVER, url, user, password, poolSize);
                    log.info("org.h2.Driver used as default");
                } catch (ClassNotFoundException e1) {
                    log.error("Default driver not found.", e1);
                    throw new RuntimeException();
                }
            }
        }
    }

    public static void dispose () throws SQLException {
        if (instance != null) {
            instance.clearConnectionQueue();
            instance = null;
            log.debug("Connection pool succesfully disposed");
        }
    }

    public static ConnectionPool getInstance () {
        return instance;
    }

    private ConnectionPool (String driver, String url, String user, String password, int poolSize) throws ClassNotFoundException, SQLException {
        Class.forName (driver);
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            Connection connection = DriverManager.getConnection(url, user, password);
            connectionQueue.offer(connection);
        }
    }

    public Connection takeConnection () throws SQLException {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            if (takeConnection().isValid(10000)) {
                takeConnection();
            } else {
                log.warn("Free connection waiting interrupted.", e);
                throw new RuntimeException();
            }
        }
        return connection;
    }

    public void releaseConnection (Connection connection) {
        try {
            if (!connection.isClosed ()) {
                if (!connectionQueue.offer(connection)) {
                    log.warn("Connection not added. Possible `leakage` of connections");
                }
            } else {
                log.info("Trying to release closed connection. Possible leakage` of connections");
            }
        } catch (SQLException e) {
            log.warn("SQLException at connection isClosed() checking. Connection not added", e);
        }
    }

    private void clearConnectionQueue () throws SQLException {
        Connection connection;
        while ((connection = connectionQueue.poll ()) != null) {
            /* see java.sql.Connection#close () javadoc */
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            connection.close();
        }
    }
}





