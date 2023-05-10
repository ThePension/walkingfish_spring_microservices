package ch.walkingfish.walkingfish.consumer.tools;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleLog implements Serializable {
    private String message;
    private String timestamp;

    public SimpleLog(
            @JsonProperty("message") String message,
            @JsonProperty("timestamp") String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return this.message;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{" +
            " message='" + getMessage() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}