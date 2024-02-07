package cs545.property.dto;

import cs545.property.domain.enums.PropertyType;
import lombok.Data;

@Data
public class PropertyUpdateRequest {
    Long id;
    //
    PropertyType propertyType;
    Double price;
    AddressDto address;
    Long ownerId;

}