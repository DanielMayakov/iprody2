package example.services;

import java.util.Arrays;
import java.util.Optional;

public enum UserAction {
    ADD_NETWORK(1, "To add new network"),
    ADD_DEVICE(2, "To add device to network"),
    CONNECT_DEVICES(3, "To add connection between devices"),
    SEARCH(4, "To search and view network objects"),
    EXIT(5, "To exit");

    private int code;
    private String description;

    UserAction(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<UserAction> valueOf(int code) {
        return Arrays.stream(values())
                .filter(action -> action.code == code)
                .findAny();
    }
}
