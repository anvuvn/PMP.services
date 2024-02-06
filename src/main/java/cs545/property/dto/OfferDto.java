package cs545.property.dto;

import cs545.property.constant.OfferStatus;
import cs545.property.constant.PropertyTransactionStatus;
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
    private int id;
    private int propertyId;

    private String propertyName;
    private PropertyTransactionStatus propertyStatus;
    private String propertyImage;
    private BigDecimal propertyPrice;
    private BigDecimal amount;
    private Date date;
    private String message;
    private OfferStatus status;
}
