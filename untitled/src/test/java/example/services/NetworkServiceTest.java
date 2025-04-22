package example.services;


import example.jdbc.NetworksDao;
import example.model.Device;
import example.model.Network;
import example.ui.ConsoleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NetworkServiceTest {

    @Mock
    private NetworksDao networkDao;

    @Mock
    private ConsoleController consoleController;

    private NetworkService networkService;

    @BeforeEach
    public void setUp() throws ClassNotFoundException {
        networkService = new NetworkService(consoleController);
        // Используем Mockito для внедрения мок-объекта
        networkService.networkDao = networkDao;
    }

    @Test
    public void testValidateDevice_ValidDevice() {
        Device validDevice = new Device(
                "Router",
                "TestDevice",
                "Active",
                "00:1A:2B:3C:4D:5E",
                "192.168.1.1"
        );

        assertDoesNotThrow(() -> networkService.validateDevice(validDevice));
    }

    @Test
    public void testValidateDevice_InvalidIpAddress() {
        Device invalidDevice = new Device(
                "Router",
                "TestDevice",
                "Active",
                "00:1A:2B:3C:4D:5E",
                "256.0.0.1"
        );

        assertThrows(IllegalArgumentException.class,
                () -> networkService.validateDevice(invalidDevice));
    }

    @Test
    public void testDeleteNetwork_NetworkWithNoDevices() throws Exception {
        Long networkId = 1L;
        when(networkDao.getDevices()).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> networkService.deleteNetwork(networkId));
    }

    @Test
    public void testDeleteNetwork_NetworkWithDevices() throws Exception {
        Long networkId = 1L;
        Device device = new Device(
                "Router",
                "TestDevice",
                "Active",
                "00:1A:2B:3C:4D:5E",
                "192.168.1.1"
        );
        device.setNetworkId(networkId);

        when(networkDao.getDevices()).thenReturn(List.of(device));

        assertThrows(IllegalStateException.class,
                () -> networkService.deleteNetwork(networkId));
    }

    @Test
    public void testAddDeviceToNetwork() throws Exception {
        Network network = new Network("TestNet", "Test Description");
        network.setId(1L);

        Device device = new Device(
                "Router",
                "TestDevice",
                "Active",
                "00:1A:2B:3C:4D:5E",
                "192.168.1.1"
        );

        when(networkDao.save(any(Device.class))).thenReturn(device);

        Device savedDevice = networkService.addDeviceToNetwork(device, network);

        assertEquals(network.getId(), savedDevice.getNetworkId());
        verify(networkDao).save(device);
    }
}

