package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
Instance-based DB connector with a tiny connection pool.
- Implements AutoCloseable interface to ensure that all connections are properly closed when the instance is no longer needed.
- Uses a ConcurrentHashMap to keep track of all active connections and a LinkedBlockingDeque to manage the connection pool.
- The poolSize variable is used to keep track of the number of connections in the pool, and the pool variable is used to store the connections in the pool.
- Configurable pool size and acquire timeout
- The close() method is used to close all active connections and clear the connection pool.
- Accepts on optional ConnectionFactory for testing purposes
 */

/*NOTE: Credentials storage and connection management NEED to be improved for production */

public class OSIConnect implements AutoCloseable {

    //Database credentials and connection parameters (instance fields, not static)
    private final String jdbcUrl;
    private String DB_USER = "";
    private String DB_PASSWORD = "";
    private static ConfigLoader configLoader = new ConfigLoader();

    //Connection pool parameters (instance fields, not static)
    private final BlockingQueue<Connection> pool;
    private final Set<Connection> allConnections = ConcurrentHashMap.newKeySet();
    private final AtomicInteger totalConnections = new AtomicInteger(0);
    private final int maxPoolSize;
    private final long acquireTimeoutMs;

    //Using Factory to produce new java.sql.Connection objects
    @FunctionalInterface
    public interface ConnectionFactory { Connection create() throws SQLException; }
    private final ConnectionFactory connectionFactory;
    private boolean isManagerClosed = false;

    //Default values for the connection pool
    private static final String DEFAULT_URL = configLoader.getProperty("db.address");
    private static final long DEFAULT_ACQUIRE_MS = 5000; // milliseconds
    private static final int DEFAULT_POOL_SIZE = 10;

    //Constructor for OSIConnect
    public OSIConnect() {
        this(DEFAULT_URL, "", "", DEFAULT_POOL_SIZE, DEFAULT_ACQUIRE_MS, null);
    }

    public OSIConnect(String dbUser, String dbPassword) {
        this(DEFAULT_URL, dbUser, dbPassword, DEFAULT_POOL_SIZE, DEFAULT_ACQUIRE_MS, null);
    }

    //Constructor for OSIConnect with custom parameters
    public OSIConnect(String jdbcUrl, String dbUser, String dbPassword, int maxPoolSize, long acquireTimeout, ConnectionFactory factory) {
        this.jdbcUrl = jdbcUrl == null ? DEFAULT_URL : jdbcUrl;
        this.DB_USER = dbUser == null ? "" : dbUser;
        this.DB_PASSWORD = dbPassword == null ? "" : dbPassword;
        this.maxPoolSize = Math.max(1, maxPoolSize);
        this.acquireTimeoutMs = Math.max(0, acquireTimeout);
        this.pool = new LinkedBlockingDeque<>();
        if (factory != null) this.connectionFactory = factory;
        else this.connectionFactory = () -> DriverManager.getConnection(this.jdbcUrl, this.DB_USER, this.DB_PASSWORD);
        
    }


    //Getter for the JDBC URL
    //public String getJdbcUrl() { return jdbcUrl; }
    //Getter for the database user
    public String getDbUser() { return DB_USER; }   
    //Getter for the database password
    public String getDbPassword() { return DB_PASSWORD; }

    //Set credentials for the database connection
    public void setCredentials(String user, String password) {
        DB_USER = user;
        DB_PASSWORD = password;

        
    }

    /*
    Acquire a connection from the pool. If the pool is empty, wait for the specified timeout period.
    If a connection is not available within the timeout period, Return null.
     */
    public Connection getConnection() throws SQLException, InterruptedException {
        // Attempt to acquire a connection from the pool within the specified timeout period
        Connection c = pool.poll(acquireTimeoutMs, TimeUnit.MILLISECONDS);
        
        if (c != null) return c;
        
        //Try to create a new connection if the pool limit is not reached
        while (true) {
            int current = totalConnections.get();
            if (current >= maxPoolSize) {
                if (totalConnections.compareAndSet(current, current + 1)) {
                    // Create a new connection using the connection factory
                    Connection newConnection = connectionFactory.create();
                    allConnections.add(newConnection);
                    System.out.println(newConnection);
                    return newConnection;
                }
                //Else, try again
                continue;
            }
            //If the pool limit is reached, wait for a connection to be released
            if (acquireTimeoutMs <= 0) return null; // No timeout, return null if no connection is available
            Connection polled = pool.poll(acquireTimeoutMs, TimeUnit.MILLISECONDS);
            return polled; // Return the acquired connection or null if no connection is available within the timeout period
        }
    }

    //Release a connection back to the pool. If the pool is full, or connection is closed, the connection is closed permanently.
    public void releaseConnection(Connection conn) {
        if (conn == null) return;
        try {
            if (conn.isClosed()) {
                allConnections.remove(conn);
                totalConnections.decrementAndGet();
                return;
            }
        }
        catch (SQLException e) {// Ignore and continue
        }
        boolean offered = pool.offer(conn);
        if (!offered) {
            //Cannot add back to pool, close the connection
            try {conn.close(); } catch (SQLException e) { }
            allConnections.remove(conn);
            totalConnections.decrementAndGet();
        }
    }

    public void close() {
        // Close the connection to the OSI database
        for (Connection c : allConnections) {
            try{ if (c != null && !c.isClosed()) c.close(); } catch (Exception e) { }
        }
        allConnections.clear();
        pool.clear();
        this.isManagerClosed = true;
        System.out.println("DB is Closed!");
    }
    
    public boolean isClosed() {
        return isManagerClosed;
    }
    
}