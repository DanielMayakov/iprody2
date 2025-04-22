package example.services;

import example.jdbc.NetworksDao;
import example.model.Device;
import example.model.Network;
import example.ui.ConsoleController;
import example.utils.NetworkValidator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class NetworkService {
    private ConsoleController consoleController;
    NetworksDao networkDao;

    public NetworkService(ConsoleController consoleController) throws ClassNotFoundException {
        networkDao = new NetworksDao();
        this.consoleController = consoleController;
    }

    public void process() {
        while(true) {
            int userChoice = consoleController.getUserChoice();
            var selectedAction = UserAction.valueOf(userChoice);

            if (selectedAction.isPresent()) {
                var action = selectedAction.get();
                if(action == UserAction.EXIT)
                    return;

                try {
                    processChoice(action);
                } catch (Exception e) {
                    consoleController.printError(e.getMessage());
                }
            } else {
                consoleController.printError("Unexpected choice!");
            }
        }
    }
    public void processSearch() throws Exception {
        int searchChoice = consoleController.getSearchChoice();
        switch (searchChoice) {
            case 1 -> searchDevices();
            case 2 -> searchNetworks();
            case 3 -> showNetworkDevices();
            case 4 -> showDeviceConnections();
            case 5 -> generateNetworkReport();
            default -> consoleController.printError("Invalid choice");
        }
    }
    private void searchDevices() throws Exception {
        String name = consoleController.readOptionalString("Enter device name (or leave empty): ");
        String ipAddress = consoleController.readOptionalString("Enter IP address (or leave empty): ");
        String type = consoleController.readOptionalString("Enter device type (or leave empty): ");
        String status = consoleController.readOptionalString("Enter device status (or leave empty): ");

        var devices = networkDao.searchDevices(name, ipAddress, type, status);
        devices.forEach(consoleController::printObject);
    }

    private void searchNetworks() throws Exception {
        String name = consoleController.readOptionalString("Enter network name (or leave empty): ");
        String description = consoleController.readOptionalString("Enter network description (or leave empty): ");

        var networks = networkDao.searchNetworks(name, description);
        networks.forEach(consoleController::printObject);
    }

    private void showNetworkDevices() throws Exception {
        var networks = networkDao.getNetworks();
        var selectedNetwork = consoleController.selectObjectById(networks, "network");

        var devices = networkDao.getDevicesByNetwork(selectedNetwork.getId());
        devices.forEach(consoleController::printObject);
    }

    private void showDeviceConnections() throws Exception {
        var devices = networkDao.getDevices();
        var selectedDevice = consoleController.selectObjectById(devices, "device");

        var connections = networkDao.getDeviceConnections(selectedDevice.getId());
        connections.forEach(consoleController::printObject);
    }

    private void generateNetworkReport() throws Exception {
        var networks = networkDao.getNetworks();
        var selectedNetwork = consoleController.selectObjectById(networks, "network");

        var report = networkDao.generateNetworkReport(selectedNetwork.getId());
        consoleController.printObject(report);
    }
    private void processChoice(UserAction action) throws Exception {
        switch (action) {
            case ADD_NETWORK -> {
                var network = consoleController.readNewNetwork();
                network = networkDao.save(network);
                consoleController.printObject(network);
            }
            case ADD_DEVICE -> {
                var networks = networkDao.getNetworks();
                var networkToSet = consoleController.selectObjectById(networks, "network");
                var deviceToAdd = consoleController.readNewDevice();
                deviceToAdd.setNetworkId(networkToSet.getId());
                deviceToAdd = networkDao.save(deviceToAdd);
                consoleController.printObject(deviceToAdd);
            }
            case CONNECT_DEVICES -> {
                var devices = networkDao.getDevices();
                var firstDevice = consoleController.selectObjectById(devices, "device to connect from");
                var secondDevice = consoleController.selectObjectById(devices, "device to connect to");
                if(Objects.equals(firstDevice.getId(), secondDevice.getId())) {
                    throw new IllegalArgumentException("Devices must be different!");
                }
                var connection = consoleController.readNewConnection();
                connection.setDeviceFromId(firstDevice.getId());
                connection.setDeviceToId(secondDevice.getId());
                connection = networkDao.save(connection);
                consoleController.printObject(connection);
            }
            case SEARCH -> processSearch();
            default -> throw new RuntimeException("Unexpected behaviour");
        }
    }

    public void validateDevice(Device device) throws IllegalArgumentException {
        if (!NetworkValidator.validateIpAddress(device.getIpAddress())) {
            throw new IllegalArgumentException("Invalid IP address format");
        }
        if (!NetworkValidator.validateMacAddress(device.getMacAddress())) {
            throw new IllegalArgumentException("Invalid MAC address format");
        }
    }

    public void deleteNetwork(Long networkId) throws Exception {
        List<Device> devices = networkDao.getDevices();
        boolean hasDevices = devices.stream()
                .anyMatch(device -> device.getNetworkId().equals(networkId));

        if (hasDevices) {
            throw new IllegalStateException("Cannot delete network with devices");
        }

        try (var connection = getConnection()) {
            try (var statement = connection.prepareStatement("DELETE FROM networks.networks WHERE id = ?")) {
                statement.setLong(1, networkId);
                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Deleting network failed, no rows affected.");
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error deleting network: " + e.getMessage(), e);
        }
    }

    // Приватный метод для получения соединения с базой данных
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/network_db", "admin", "admin");
    }

    public Device addDeviceToNetwork(Device device, Network network) throws Exception {
        validateDevice(device);
        device.setNetworkId(network.getId());
        return networkDao.save(device);
    }
}
