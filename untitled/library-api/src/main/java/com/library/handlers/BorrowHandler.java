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

public class BorrowHandler implements HttpHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        try {
            if ("POST".equalsIgnoreCase(method)) {
                handleBorrowBook(exchange);
            } else if ("PUT".equalsIgnoreCase(method)) {
                handleReturnBook(exchange);
            } else {
                sendResponse(exchange, 405, "Method Not Allowed");
            }
        } catch (Exception e) {
            sendResponse(exchange, 500, "Internal Server Error: " + e.getMessage());
        }
    }

    private void handleBorrowBook(HttpExchange exchange) throws Exception {
        Map<String, Integer> requestBody = objectMapper.readValue(exchange.getRequestBody(), Map.class);

        int bookId = requestBody.get("book_id");
        int readerId = requestBody.get("reader_id");

        Connection connection = DatabaseUtils.getConnection();
        PreparedStatement checkAvailability = connection.prepareStatement(
                "SELECT available FROM books WHERE id = ?");
        checkAvailability.setInt(1, bookId);

        ResultSet resultSet = checkAvailability.executeQuery();
        if (!resultSet.next() || !resultSet.getBoolean("available")) {
            sendResponse(exchange, 400, "Book is not available for borrowing");
            return;
        }

        PreparedStatement borrowBook = connection.prepareStatement(
                "INSERT INTO borrowed_books (book_id, reader_id) VALUES (?, ?)");
        borrowBook.setInt(1, bookId);
        borrowBook.setInt(2, readerId);
        borrowBook.executeUpdate();

        PreparedStatement updateBookStatus = connection.prepareStatement(
                "UPDATE books SET available = false WHERE id = ?");
        updateBookStatus.setInt(1, bookId);
        updateBookStatus.executeUpdate();

        sendResponse(exchange, 201, "Book borrowed successfully");
    }

    private void handleReturnBook(HttpExchange exchange) throws Exception {
        Map<String, Integer> requestBody = objectMapper.readValue(exchange.getRequestBody(), Map.class);

        int bookId = requestBody.get("book_id");

        Connection connection = DatabaseUtils.getConnection();

        PreparedStatement deleteBorrowRecord = connection.prepareStatement(
                "DELETE FROM borrowed_books WHERE book_id = ?");
        deleteBorrowRecord.setInt(1, bookId);

        int rowsAffected = deleteBorrowRecord.executeUpdate();

        if (rowsAffected > 0) {
            PreparedStatement updateBookStatus = connection.prepareStatement(
                    "UPDATE books SET available = true WHERE id = ?");
            updateBookStatus.setInt(1, bookId);
            updateBookStatus.executeUpdate();

            sendResponse(exchange, 200, "Book returned successfully");
        } else {
            sendResponse(exchange, 404, "Borrow record not found");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
