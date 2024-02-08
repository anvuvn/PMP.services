package cs545.property.dto;

import cs545.property.constant.OfferStatus;
import cs545.property.constant.PropertyStatus;
import cs545.property.constant.PropertyTransactionStatus;
import cs545.property.domain.Offer;
import cs545.property.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {
    private Long id;
    private Long propertyId;

    private String propertyName;
    private PropertyStatus propertyStatus;
    private String propertyImage;
    private Double propertyPrice;
    private BigDecimal amount;
    private Date date;
    private String message;
    private OfferStatus status;

    public OfferDto(Offer offer){
        id = offer.getId();
        propertyId = offer.getProperty().getId();
        propertyName = null;
        propertyStatus = offer.getProperty().getStatus();
        propertyImage = null;
        propertyPrice = offer.getProperty().getPrice();
        date = offer.getDate();
        message = offer.getMessage();
        status = offer.getStatus();
    }

}
