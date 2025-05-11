package com.home.mec888.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private final Connection connection;

    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mec888", "root", "");
    }

    public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
        return dbConnection != null ? dbConnection : (dbConnection = new DBConnection());
    }

    public Connection getConn() {
        return connection;
    }
}
