package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/library";
    private static final String DB_USER = "your_username"; // Укажите имя пользователя базы данных
    private static final String DB_PASSWORD = "your_password"; // Укажите пароль базы данных

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
