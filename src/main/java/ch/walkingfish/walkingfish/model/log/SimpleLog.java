package ch.walkingfish.walkingfish.model.log;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleLog implements Serializable {
    private String action;
    private String timestamp;
    private LogType type;
    private String message;

    /**
     * Constructor for SimpleLog
     * @param type the type of the log
     * @param action the action performed
     * @param message the message of the log
     */
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

    /**
     * Get the action of the log
     * @return the action of the log
     */
    public String getAction() {
        return this.action;
    }

    /**
     * Get the timestamp of the log
     * @return the timestamp of the log as a string
     */
    public String getTimestamp() {
        return this.timestamp;
    }

    /**
     * Get the type of the log
     * @return the type of the log
     */
    public LogType getType() {
        return this.type;
    }

    /**
     * Get the message of the log
     * @return the message of the log
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Set the action of the log
     * @param action the action of the log
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Set the timestamp of the log
     * @param timestamp the timestamp of the log
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Set the type of the log
     * @param type the type of the log
     */
    public void setType(LogType type) {
        this.type = type;
    }

    /**
     * Set the message of the log
     * @param message the message of the log
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get the string representation of the log
     * @return the string representation of the log
     */
    @Override
    public String toString() {
        return getTimestamp() + " [" + getType().getLabel() + "] " + getAction() + " : " + getMessage();
    }
}