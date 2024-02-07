package cs545.property.constant;

public enum PropertyStatus {
    Waiting("Waiting"),
    Available("Available"),
    Contingent("Contingent"),
    Sold("Sold");

    private String status;
    PropertyStatus(String status){
        this.status = status;
    }
}
