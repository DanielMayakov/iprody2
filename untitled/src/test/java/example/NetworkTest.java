package example;

import example.jdbc.NetworksDao;
import example.model.Network;
import example.ui.ConsoleController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Scanner;

public class NetworkTest {

    @Test
    public void testNetworkSave() throws SQLException, ClassNotFoundException {
        var networkToSave = new Network("some_name", "some_description");
        var networkService = new NetworksDao();
        networkService.save(networkToSave);
    }
    @Test
    public void testNetworkInput() {
        var networkToSave = new ConsoleController(new Scanner(System.in)).readNewNetwork();
        assert networkToSave.getName().equals("new_name");
        assert networkToSave.getDescription().equals("new_description");
    }
}
