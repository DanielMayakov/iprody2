package example.jdbc;

import example.model.Device;
import example.model.DeviceConnection;
import example.model.Network;
import example.model.NetworkReport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NetworksDao {

    public NetworksDao() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/network_db", "admin", "admin");
    }

    public Network save(Network networkToSave) throws SQLException {
        try (var connection = getConnection()) {
            try (var statement = connection.prepareStatement("insert into networks.networks (name, description) values(?,?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, networkToSave.getName());
                statement.setString(2, networkToSave.getDescription());
                statement.execute();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                networkToSave.setId(generatedKeys.getLong("id"));
                networkToSave.setCreatedAt(generatedKeys.getDate("created_at"));
            }
        }

        return networkToSave;
    }

    public Device save(Device deviceToSave) throws SQLException {
        try (var connection = getConnection()) {
            try (var statement = connection.prepareStatement("insert into networks.devices (network_id, name, ip_address, mac_address, type, status) values(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, deviceToSave.getNetworkId());
                statement.setString(2, deviceToSave.getName());
                statement.setString(3, deviceToSave.getIpAddress());
                statement.setString(4, deviceToSave.getMacAddress());
                statement.setString(5, deviceToSave.getType());
                statement.setString(6, deviceToSave.getStatus());
                statement.execute();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                deviceToSave.setId(generatedKeys.getLong("id"));
                deviceToSave.setCreatedAt(generatedKeys.getDate("created_at"));
            }
        }

        return deviceToSave;
    }

    public DeviceConnection save(DeviceConnection deviceConnection) throws SQLException {
        try (var connection = getConnection()) {
            try (var statement = connection.prepareStatement("insert into networks.connections (type, status, device_from_id, device_to_id) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, deviceConnection.getType());
                statement.setString(2, deviceConnection.getStatus());
                statement.setLong(3, deviceConnection.getDeviceFromId());
                statement.setLong(4, deviceConnection.getDeviceToId());
                statement.execute();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                deviceConnection.setId(generatedKeys.getLong("id"));
                deviceConnection.setCreatedAt(generatedKeys.getDate("created_at"));
            }
        }

        return deviceConnection;
    }

    public List<Network> getNetworks() throws SQLException {
        try (var connection = getConnection()) {
            try (var statement = connection.createStatement()) {
                var resultSet = statement.executeQuery("Select * from networks.networks");
                var list = new ArrayList<Network>();
                while (resultSet.next()) {
                    var id = resultSet.getLong("id");
                    var name = resultSet.getString("name");
                    var description = resultSet.getString("description");
                    var createdAt = resultSet.getDate("created_at");
                    list.add(new Network(id, name, description, createdAt));
                }
                return list;
            }
        }
    }

    public List<Device> getDevices() throws SQLException {
        try (var connection = getConnection()) {
            try (var statement = connection.createStatement()) {
                var resultSet = statement.executeQuery("Select * from networks.devices");
                var list = new ArrayList<Device>();
                while (resultSet.next()) {
                    var id = resultSet.getLong("id");
                    var name = resultSet.getString("name");
                    var networkId = resultSet.getLong("network_id");
                    var ip = resultSet.getString("ip_address");
                    var mac = resultSet.getString("mac_address");
                    var type = resultSet.getString("type");
                    var status = resultSet.getString("status");
                    var createdAt = resultSet.getDate("created_at");

                    list.add(new Device(id, name, networkId, type, status, mac, ip, createdAt));
                }
                return list;
            }
        }
    }
    public List<Device> searchDevices(String name, String ipAddress, String type, String status) throws SQLException {
        try (var connection = getConnection()) {
            var query = new StringBuilder("SELECT * FROM networks.devices WHERE 1=1");
            List<String> conditions = new ArrayList<>();
            List<Object> params = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                conditions.add("name LIKE ?");
                params.add("%" + name + "%");
            }
            if (ipAddress != null && !ipAddress.isEmpty()) {
                conditions.add("ip_address = ?");
                params.add(ipAddress);
            }
            if (type != null && !type.isEmpty()) {
                conditions.add("type = ?");
                params.add(type);
            }
            if (status != null && !status.isEmpty()) {
                conditions.add("status = ?");
                params.add(status);
            }

            for (String condition : conditions) {
                query.append(" AND ").append(condition);
            }

            try (var statement = connection.prepareStatement(query.toString())) {
                for (int i = 0; i < params.size(); i++) {
                    statement.setObject(i + 1, params.get(i));
                }

                var resultSet = statement.executeQuery();
                var list = new ArrayList<Device>();
                while (resultSet.next()) {
                    var id = resultSet.getLong("id");
                    var nameStr = resultSet.getString("nameStr");
                    var networkId = resultSet.getLong("network_id");
                    var ip = resultSet.getString("ip_address");
                    var mac = resultSet.getString("mac_address");
                    var typeStr = resultSet.getString("type");
                    var statusStr = resultSet.getString("status");
                    var createdAt = resultSet.getDate("created_at");

                    list.add(new Device(id, nameStr, networkId, typeStr, statusStr, mac, ip, createdAt));
                }
                return list;
            }
        }
    }

    public List<Network> searchNetworks(String name, String description) throws SQLException {
        try (var connection = getConnection()) {
            var query = new StringBuilder("SELECT * FROM networks.networks WHERE 1=1");
            List<String> conditions = new ArrayList<>();
            List<Object> params = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                conditions.add("name LIKE ?");
                params.add("%" + name + "%");
            }
            if (description != null && !description.isEmpty()) {
                conditions.add("description LIKE ?");
                params.add("%" + description + "%");
            }

            for (String condition : conditions) {
                query.append(" AND ").append(condition);
            }

            try (var statement = connection.prepareStatement(query.toString())) {
                for (int i = 0; i < params.size(); i++) {
                    statement.setObject(i + 1, params.get(i));
                }

                var resultSet = statement.executeQuery();
                var list = new ArrayList<Network>();
                while (resultSet.next()) {
                    var id = resultSet.getLong("id");
                    var nameStr = resultSet.getString("name");
                    var descriptionStr = resultSet.getString("description");
                    var createdAt = resultSet.getDate("created_at");
                    list.add(new Network(id, nameStr, descriptionStr, createdAt));
                }
                return list;
            }
        }
    }
    public List<DeviceConnection> getDeviceConnections(Long deviceId) throws SQLException {        try (var connection = getConnection()) {
        try (var statement = connection.prepareStatement("SELECT * FROM networks.connections WHERE device_from_id = ? OR device_to_id = ?")) {
            statement.setLong(1, deviceId);
            statement.setLong(2, deviceId);

            var resultSet = statement.executeQuery();
            var list = new ArrayList<DeviceConnection>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var deviceFromId = resultSet.getLong("device_from_id");
                var deviceToId = resultSet.getLong("device_to_id");
                var status = resultSet.getString("status");
                var type = resultSet.getString("type");
                var createdAt = resultSet.getDate("created_at");

                list.add(new DeviceConnection(id, deviceFromId, deviceToId, status, type, createdAt));
            }
            return list;
        }
    }
    }

    public List<Device> getDevicesByNetwork(Long networkId) throws SQLException {
        try (var connection = getConnection()) {
            try (var statement = connection.prepareStatement("SELECT * FROM networks.devices WHERE network_id = ?")) {
                statement.setLong(1, networkId);

                var resultSet = statement.executeQuery();
                var list = new ArrayList<Device>();
                while (resultSet.next()) {
                    var id = resultSet.getLong("id");
                    var name = resultSet.getString("name");
                    var ip = resultSet.getString("ip_address");
                    var mac = resultSet.getString("mac_address");
                    var type = resultSet.getString("type");
                    var status = resultSet.getString("status");
                    var createdAt = resultSet.getDate("created_at");

                    list.add(new Device(id, name, networkId, type, status, mac, ip, createdAt));
                }
                return list;
            }
        }
    }

    public NetworkReport generateNetworkReport(Long networkId) throws SQLException {
        try (var connection = getConnection()) {
            var devices = getDevicesByNetwork(networkId);

            long totalDevices = devices.size();
            long activeDevices = devices.stream().filter(d -> "active".equalsIgnoreCase(d.getStatus())).count();
            long inactiveDevices = totalDevices - activeDevices;

            return new NetworkReport(networkId, totalDevices, activeDevices, inactiveDevices);
        }
    }
}
