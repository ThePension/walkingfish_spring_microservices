package ch.walkingfish.walkingfish.model.log;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleLog implements Serializable {
    private String text;
    // private String timestamp;

    // public SimpleLog(
    //         @JsonProperty("message") String message,
    //         @JsonProperty("timestamp") String timestamp) {
    //     this.message = message;
    //     this.timestamp = timestamp;
    // }

    public SimpleLog(
        @JsonProperty("text") String text) {

        this.text = text;
        
        // Now
        // this.timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    }

    public String getText() {
        return this.text;
    }

    // public String getTimestamp() {
    //     return this.timestamp;
    // }

    public void setText(String text) {
        this.text = text;
    }

    // public void setTimestamp(String timestamp) {
    //     this.timestamp = timestamp;
    // }

    @Override
    public String toString() {
        return "{" +
            " text='" + getText() + "'" +
            // ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}