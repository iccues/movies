package com.iccues.movie.backend.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String DB_HOST = System.getenv("DB_HOST");
    private static String DB_PORT = System.getenv("DB_PORT");
    private static String DB_NAME = System.getenv("DB_NAME");

    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    private static final String DB_URL;
    private static final Connection CONNECTION;

    static {
        if (DB_PORT == null) {
            DB_PORT = "3306";
        }
        if (DB_NAME == null) {
            DB_NAME = "";
        }

        DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            CONNECTION = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return CONNECTION;
    }

    public static Statement getStatement() throws SQLException {
        return CONNECTION.createStatement();
    }

}
