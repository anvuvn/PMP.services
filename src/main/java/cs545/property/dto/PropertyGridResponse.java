package cs545.property.dto;

import cs545.property.constant.PropertyStatus;
import cs545.property.domain.Property;
import cs545.property.domain.enums.PropertyType;
import cs545.property.util.ImageUtil;
import lombok.Data;

@Data
public class PropertyGridResponse {
    Long id;
    PropertyType propertyType;
    Double price;
    AddressDto address;
    Long ownerId;
    String ownerName;
    PropertyStatus status;

    byte[] thumbs;
    public PropertyGridResponse(Property p) {
        id = p.getId();
        propertyType = p.getPropertyType();
        price = p.getPrice();
        address = new AddressDto(p.getAddress());
        ownerId = p.getOwner().getId();
        ownerName = p.getOwner().getName();
        status = p.getStatus();
        var imps = p.getImages();
        if(imps != null && imps.size() > 0){
            thumbs =  ImageUtil.decompressImage(imps.get(0).getImageData());
        }
    }
}
