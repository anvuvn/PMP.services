package cs545.property.dto;

import cs545.property.domain.Address;
import cs545.property.domain.PropertyImage;
import cs545.property.domain.enums.PropertyType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class PropertyAddRequest {
    Long id;
    PropertyType propertyType;
    Double price;
    AddressDto address;
    Long ownerId;

}