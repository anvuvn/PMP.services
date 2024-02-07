package cs545.property.dto;

import cs545.property.domain.enums.PropertyType;
import lombok.Data;

@Data
public class PropertySearchRequest {
    Double minPrice;
    Double maxPrice;
    String propertyType;
    String location;
    Integer numberOfRoom;
}
