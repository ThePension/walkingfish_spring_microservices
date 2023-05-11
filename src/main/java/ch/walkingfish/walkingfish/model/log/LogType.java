package ch.walkingfish.walkingfish.model.log;

public enum LogType {
    INFO("INFO"),
    DEBUG("DEBUG"),
    WARNING("WARNING"),
    ERROR("ERROR");

    private String label;

    LogType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}