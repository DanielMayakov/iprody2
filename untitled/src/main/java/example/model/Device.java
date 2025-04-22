package example.model;

import java.util.Date;

public class Device implements ModelInterface{
    private Long id;
    private Long networkId;
    private String name;
    private String type;
    private String status;
    private String macAddress;
    private String ipAddress;
    private Date createdAt;

    public Device(String type, String name, String status, String macAddress, String ipAddress) {
        this.type = type;
        this.status = status;
        this.macAddress = macAddress;
        this.name = name;
        this.ipAddress = ipAddress;
    }

    public Device(Long id, String name, Long networkId, String type, String status, String macAddress, String ipAddress, Date createdAt) {
        this.id = id;
        this.name = name;
        this.networkId = networkId;
        this.type = type;
        this.status = status;
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.createdAt = createdAt;
    }


    public Long getId() {
        return id;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setNetworkId(Long networkId) {
        this.networkId = networkId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", networkId=" + networkId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

