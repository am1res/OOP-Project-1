package company.data;

import company.data.interfaces.IDB;

import java.sql.*;

public class PostgresDB implements IDB {
    private String host;
    private String username;
    private String password;
    private String dbName;
    private Connection connection;

    // ✅ ADD THIS CONSTRUCTOR
    public PostgresDB(String host, String username, String password, String dbName) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.dbName = dbName;
        this.connection = null;
    }

    // Keep the default constructor for backward compatibility (optional)
    public PostgresDB() {
        this.connection = null;
    }

    @Override
    public Connection getConnection() {
        String connectionUrl = host + "/" + dbName;
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            // Here we load the driver's class file into memory at the runtime
            Class.forName("org.postgresql.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(connectionUrl, username, password);

            return connection;
        } catch (Exception e) {
            System.out.println("❌ Failed to connect to postgres: " + e.getMessage());
            return null;
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("❌ Connection close error: " + ex.getMessage());
            }
        }
    }
}
