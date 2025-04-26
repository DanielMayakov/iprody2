package com.library;

import com.library.handlers.BookHandler;
import com.library.handlers.BorrowHandler;
import com.library.handlers.ReaderHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class LibraryServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Ресурсы
        server.createContext("/books", new BookHandler());
        server.createContext("/readers", new ReaderHandler());
        server.createContext("/borrowed", new BorrowHandler());

        server.setExecutor(null); // Используем стандартный Executor
        server.start();
        System.out.println("Server started on port 8080");
    }
}
