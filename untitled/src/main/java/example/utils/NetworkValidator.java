package example.utils;

import java.util.regex.Pattern;

public class NetworkValidator {
    private static final String IP_REGEX =
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    private static final String MAC_REGEX =
            "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";

    public static boolean validateIpAddress(String ipAddress) {
        return ipAddress != null && Pattern.matches(IP_REGEX, ipAddress);
    }

    public static boolean validateMacAddress(String macAddress) {
        return macAddress != null && Pattern.matches(MAC_REGEX, macAddress);
    }
}

