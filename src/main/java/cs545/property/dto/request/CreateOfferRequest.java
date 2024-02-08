package cs545.property.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOfferRequest {
    private Long userId;
    private Long propertyId;
    private BigDecimal amount;
    private String message;
}
