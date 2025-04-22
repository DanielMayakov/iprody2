package example.ui;

import example.model.Device;
import example.model.DeviceConnection;
import example.model.ModelInterface;
import example.model.Network;
import example.services.UserAction;

import java.util.List;
import java.util.Scanner;

public class ConsoleController {
    private final Scanner scanner;

    public ConsoleController(Scanner scanner) {
        this.scanner = scanner;
    }

    public Network readNewNetwork() {
        String name = readString("Enter network name: ", scanner);
        String description = readString("Enter network description:", scanner);

        return new Network(name, description);
    }

    public DeviceConnection readNewConnection() {
        String type = readString("Enter type: ", scanner);
        String status = readString("Enter status:", scanner);

        return new DeviceConnection(type, status);
    }

    public Device readNewDevice() {
        String name = readString("Enter name: ", scanner);
        String ip_address = readString("Enter ip:", scanner);
        String mac_address = readString("Enter mac:", scanner);
        String type = readString("Enter status:", scanner);
        String status = readString("Enter type:", scanner);

        return new Device(type, name, status, mac_address, ip_address);
    }

    public int getUserChoice() {
        System.out.println("Enter to do: ");
        for (var action : UserAction.values()) {
            System.out.println(action.getCode() + " " + action.getDescription());
        }

        var value = scanner.nextInt();
        scanner.nextLine(); //to clear buffer according to https://www.freecodecamp.org/news/java-scanner-nextline-call-gets-skipped-solved/
        return value;
    }

    public <T extends ModelInterface> T selectObjectById(List<T> models, String objectType) {
        System.out.println("Select " + objectType + " by id:");
        while (true) {
            for (var network : models) {
                System.out.println(network.toString());
            }
            var idToFetch = scanner.nextInt();
            T objectToSet = null;

            for (var model : models) {
                if (model.getId() == idToFetch) {
                    objectToSet = model;
                    break;
                }
            }

            if (objectToSet == null)
                System.out.println("Incorrect id; try again");
            else
                return objectToSet;
        }
    }

    public void printObject(Object object) {
        System.out.println(object.toString());
    }

    public void printError(String error) {
        System.out.println(error);
    }


    private static String readString(String request, Scanner scanner) {
        System.out.println(request);
        return scanner.next();
    }

    private static int readInt(String request, Scanner scanner) {
        System.out.println(request);
        return scanner.nextInt();
    }
    public int getSearchChoice() {
        System.out.println("Select search option:");
        System.out.println("1. Search Devices");
        System.out.println("2. Search Networks");
        System.out.println("3. Show Network Devices");
        System.out.println("4. Show Device Connections");
        System.out.println("5. Generate Network Report");

        var value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public String readOptionalString(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : input;
    }
}
