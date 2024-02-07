package cs545.property.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cs545.property.constant.PropertyStatus;
import cs545.property.domain.Address;
import cs545.property.domain.ImageData;
import cs545.property.domain.Offer;
import cs545.property.domain.Users;
import cs545.property.domain.enums.PropertyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOfferListDto {

//p.owner_id,u.name,p.property_type,a.city,f.*

//    private long ownerId;
//    private String  ownerName;
//    private String propertyType;
//    private String city;
//    private long offerId;
//    private BigDecimal amount;
//    private Date offerDate;
//    private String offerStatus;
//    private String message;
//    private String customerName;
//    private long customerId;
    private long customer_id;
    private String name;
    private String Status;
    private BigDecimal amount;
    private String message;
    private long property_id;

    //property
//    Long id;
//    PropertyType propertyType;
//    Double price;
//    Address address;
//    Users owner;//owner
//    PropertyStatus status;
//    private List<Offer> offers;//offers


}
