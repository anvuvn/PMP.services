package cs545.property.constant;

public enum PropertyType {
    House("House"),
    Condo("Condo"),
    Townhome("Townhome"),
    MultiFamily("Multi family"),
    Mobile("Mobile"),
    Farm("Farm"),
    Land("Land");

    private String type;

    PropertyType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
