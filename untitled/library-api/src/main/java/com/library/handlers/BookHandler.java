package com.library.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookHandler implements HttpHandler {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/library";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if ("POST".equalsIgnoreCase(method)) {
            handlePost(exchange);
        } else if ("GET".equalsIgnoreCase(method)) {
            handleGet(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> requestBody = mapper.readValue(exchange.getRequestBody(), HashMap.class);

        String title = (String) requestBody.get("title");
        String author = (String) requestBody.get("author");
        int publishedYear = (int) requestBody.get("published_year");
        String genre = (String) requestBody.get("genre");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO books (title, author, published_year, genre) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, title);
                stmt.setString(2, author);
                stmt.setInt(3, publishedYear);
                stmt.setString(4, genre);
                stmt.executeUpdate();
            }
            exchange.sendResponseHeaders(201, -1); // Created
        } catch (SQLException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1); // Internal Server Error
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        Map<String, String> params = parseQuery(query);

        int readerId = Integer.parseInt(params.get("reader_id"));

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT b.book_id, b.title, b.author FROM books b " +
                    "JOIN borrowed_books bb ON b.book_id = bb.book_id " +
                    "WHERE bb.reader_id = ? AND bb.status = 'borrowed'";
            List<Map<String, Object>> books = new ArrayList<>();
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, readerId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Map<String, Object> book = new HashMap<>();
                    book.put("book_id", rs.getInt("book_id"));
                    book.put("title", rs.getString("title"));
                    book.put("author", rs.getString("author"));
                    books.add(book);
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            String response = mapper.writeValueAsString(books);

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (SQLException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1); // Internal Server Error
        }
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> params = new HashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                params.put(keyValue[0], keyValue[1]);
            }
        }
        return params;
    }
}
