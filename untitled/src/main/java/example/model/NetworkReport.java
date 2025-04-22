package example.model;

public class NetworkReport {
    private Long networkId;
    private long totalDevices;
    private long activeDevices;
    private long inactiveDevices;

    public NetworkReport(Long networkId, long totalDevices, long activeDevices, long inactiveDevices) {
        this.networkId = networkId;
        this.totalDevices = totalDevices;
        this.activeDevices = activeDevices;
        this.inactiveDevices = inactiveDevices;
    }

    @Override
    public String toString() {
        return "NetworkReport{" +
                "networkId=" + networkId +
                ", totalDevices=" + totalDevices +
                ", activeDevices=" + activeDevices +
                ", inactiveDevices=" + inactiveDevices +
                '}';
    }

    // Геттеры
    public Long getNetworkId() { return networkId; }
    public long getTotalDevices() { return totalDevices; }
    public long getActiveDevices() { return activeDevices; }
    public long getInactiveDevices() { return inactiveDevices; }
}
