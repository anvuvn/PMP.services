package cs545.property.dto;

import cs545.property.domain.Property;
import cs545.property.domain.enums.PropertyType;
import lombok.Data;

@Data
public class PropertyResponseDto {
    Long id;
    PropertyType propertyType;
    Double price;
    AddressDto address;

    public PropertyResponseDto(Property p){
        id = p.getId();
        propertyType = p.getPropertyType();
        price = p.getPrice();
        address = new AddressDto(p.getAddress());
    }
}
