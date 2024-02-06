package cs545.property.domain.enums;

public enum PropertyType {
    House("House"),
    Condo("Condo"),
    Townhome("Town home"),
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
