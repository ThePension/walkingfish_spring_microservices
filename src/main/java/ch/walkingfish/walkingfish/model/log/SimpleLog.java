package ch.walkingfish.walkingfish.model.log;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleLog implements Serializable {
    private String action;
    private String timestamp;
    private LogType type;
    private String message;

    public SimpleLog(
            @JsonProperty("type") LogType type,
            @JsonProperty("action") String action,
            @JsonProperty("message") String message) {

        this.action = action;
        this.type = type;
        this.message = message;

        // Now
        this.timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    }

    public String getAction() {
        return this.action;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public LogType getType() {
        return this.type;
    }

    public String getMessage() {
        return this.message;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                " action='" + getAction() + "'" +
                ", timestamp='" + getTimestamp() + "'" +
                ", type='" + getType() + "'" +
                "}";
    }
}