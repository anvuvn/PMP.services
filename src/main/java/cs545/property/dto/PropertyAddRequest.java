package cs545.property.dto;

import cs545.property.constant.PropertyType;
import lombok.Data;

@Data
public class PropertyAddRequest {
    Long id;
    PropertyType propertyType;
    Double price;
    AddressDto address;
    Long ownerId;

    Integer numberOfRoom;

}