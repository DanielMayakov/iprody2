package example.model;

import java.util.Date;

public class DeviceConnection {
    private Long id;
    private Long deviceFromId;
    private Long deviceToId;
    private String status;
    private String type;
    private Date createdAt;

    public DeviceConnection(String status, String type) {
        this.status = status;
        this.type = type;
    }

    public DeviceConnection(Long id, Long deviceFromId, Long deviceToId, String status, String type, Date createdAt) {
        this.id = id;
        this.deviceFromId = deviceFromId;
        this.deviceToId = deviceToId;
        this.status = status;
        this.type = type;
        this.createdAt = createdAt;
    }

    public void setDeviceToId(Long deviceToId) {
        this.deviceToId = deviceToId;
    }

    public void setDeviceFromId(Long deviceFromId) {
        this.deviceFromId = deviceFromId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getDeviceFromId() {
        return deviceFromId;
    }

    public Long getDeviceToId() {
        return deviceToId;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "DeviceConnection{" +
                "id=" + id +
                ", deviceFromId=" + deviceFromId +
                ", deviceToId=" + deviceToId +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
