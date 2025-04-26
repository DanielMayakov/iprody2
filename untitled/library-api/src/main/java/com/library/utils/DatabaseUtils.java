package com.library.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {

    // Конфигурация базы данных
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library"; // URL для подключения к базе данных
    private static final String DB_USER = "root"; // Имя пользователя базы данных
    private static final String DB_PASSWORD = "password"; // Пароль для базы данных

    static {
        try {
            // Регистрация драйвера базы данных (например, MySQL)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не удалось загрузить драйвер базы данных", e);
        }
    }

    /**
     * Получить соединение с базой данных.
     *
     * @return объект Connection
     * @throws SQLException если не удается подключиться к базе данных
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
