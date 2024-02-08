package cs545.property.dto;

import lombok.Data;

@Data
public class PropertySearchRequest {
    Double minPrice;
    Double maxPrice;
    String propertyType;
    String location;
    Integer numberOfRoom;
}
