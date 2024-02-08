package cs545.property.dto;

import cs545.property.constant.PropertyStatus;
import cs545.property.domain.Property;
import cs545.property.constant.PropertyType;
import lombok.Data;

@Data
public class PropertyResponseDto {
    Long id;
    PropertyType propertyType;
    Double price;
    AddressDto address;
    Long ownerId;
    String ownerName;
    PropertyStatus status;
    Integer numberOfRoom;

    public PropertyResponseDto(Property p) {
        id = p.getId();
        propertyType = p.getPropertyType();
        price = p.getPrice();
        address = new AddressDto(p.getAddress());
        ownerId = p.getOwner().getId();
        ownerName = p.getOwner().getName();
        numberOfRoom = p.getNumberOfRoom();
        status = p.getStatus();
    }
}
