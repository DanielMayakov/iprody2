package org.example;

import java.io.*;
import java.net.*;

public class EchoClient {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 7;

    public static void main(String[] args) throws IOException {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            System.out.println("Connected to the server at " + SERVER_ADDRESS + ":" + SERVER_PORT);

            Thread receiverThread = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println("Received from server: " + response);
                    }
                } catch (IOException e) {
                    System.err.println("Connection closed by server.");
                }
            });
            receiverThread.start();

            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                out.println(userInput); // Отправляем сообщение на сервер
            }
        } catch (IOException e) {
            System.err.println("Client exception: " + e.getMessage());
        }
    }
}


