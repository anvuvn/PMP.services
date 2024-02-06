package cs545.property.dto;

import lombok.Data;

@Data
public class FavouriteCreateRequest {
    Long userId;
    Long propertyId;
}
