package cs545.property.domain.enums;

public enum PropertyStatus {
    Waiting("Waiting"),
    Available("Available"),
    Pending("Pending"),
    Contingent("Contingent"),
    Sold("Sold");
    private String type;
    PropertyStatus(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
