package cs545.property.dto.request;

import cs545.property.constant.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeOfferStatusRequest {
    OfferStatus status;
}
