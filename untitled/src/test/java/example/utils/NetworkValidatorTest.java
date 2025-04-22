package example.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NetworkValidatorTest {

    @Test
    public void testValidIpAddress() {
        assertTrue(NetworkValidator.validateIpAddress("192.168.1.1"));
        assertTrue(NetworkValidator.validateIpAddress("255.255.255.0"));
    }

    @Test
    public void testInvalidIpAddress() {
        assertFalse(NetworkValidator.validateIpAddress("256.0.0.1"));
        assertFalse(NetworkValidator.validateIpAddress("192.168.1"));
        assertFalse(NetworkValidator.validateIpAddress(""));
        assertFalse(NetworkValidator.validateIpAddress(null));
    }

    @Test
    public void testValidMacAddress() {
        assertTrue(NetworkValidator.validateMacAddress("00:1A:2B:3C:4D:5E"));
        assertTrue(NetworkValidator.validateMacAddress("00-1A-2B-3C-4D-5E"));
    }

    @Test
    public void testInvalidMacAddress() {
        assertFalse(NetworkValidator.validateMacAddress("00:1A:2B:3C:4D"));
        assertFalse(NetworkValidator.validateMacAddress("00:1A:2B:3C:4D:5E:FF"));
        assertFalse(NetworkValidator.validateMacAddress(""));
        assertFalse(NetworkValidator.validateMacAddress(null));
    }
}
