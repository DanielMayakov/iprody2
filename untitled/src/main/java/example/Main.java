package example;

import example.services.NetworkService;
import example.ui.ConsoleController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        try(Scanner scanner = new Scanner(System.in)) {
            var consoleController = new ConsoleController(scanner);
            var service = new NetworkService(consoleController);
            service.process();
        }
    }
}