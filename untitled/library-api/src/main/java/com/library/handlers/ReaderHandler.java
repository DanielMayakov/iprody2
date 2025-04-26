package com.library.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.utils.DatabaseUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ReaderHandler implements HttpHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        try {
            if ("GET".equalsIgnoreCase(method)) {
                handleGetReaders(exchange);
            } else if ("POST".equalsIgnoreCase(method)) {
                handleAddReader(exchange);
            } else if ("DELETE".equalsIgnoreCase(method)) {
                handleDeleteReader(exchange);
            } else {
                sendResponse(exchange, 405, "Method Not Allowed");
            }
        } catch (Exception e) {
            sendResponse(exchange, 500, "Internal Server Error: " + e.getMessage());
        }
    }

    private void handleGetReaders(HttpExchange exchange) throws Exception {
        Connection connection = DatabaseUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM readers");
        ResultSet resultSet = statement.executeQuery();

        Map<Integer, Map<String, String>> readers = new HashMap<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Map<String, String> readerData = new HashMap<>();
            readerData.put("name", resultSet.getString("name"));
            readerData.put("email", resultSet.getString("email"));
            readers.put(id, readerData);
        }

        sendResponse(exchange, 200, objectMapper.writeValueAsString(readers));
    }

    private void handleAddReader(HttpExchange exchange) throws Exception {
        Map<String, String> requestBody = objectMapper.readValue(exchange.getRequestBody(), Map.class);

        String name = requestBody.get("name");
        String email = requestBody.get("email");

        Connection connection = DatabaseUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO readers (name, email) VALUES (?, ?)");
        statement.setString(1, name);
        statement.setString(2, email);
        statement.executeUpdate();

        sendResponse(exchange, 201, "Reader added successfully");
    }

    private void handleDeleteReader(HttpExchange exchange) throws Exception {
        String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.startsWith("id=")) {
            sendResponse(exchange, 400, "Bad Request: Missing 'id' parameter");
            return;
        }

        int id = Integer.parseInt(query.split("=")[1]);

        Connection connection = DatabaseUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM readers WHERE id = ?");
        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            sendResponse(exchange, 200, "Reader deleted successfully");
        } else {
            sendResponse(exchange, 404, "Reader not found");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
