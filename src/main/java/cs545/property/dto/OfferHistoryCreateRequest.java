package cs545.property.dto;

import cs545.property.constant.OfferStatus;
import cs545.property.domain.Offer;
import cs545.property.domain.Users;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OfferHistoryCreateRequest {
    Long id;


    Long offerId;


    OfferStatus status;

    LocalDateTime createdDate = LocalDateTime.now();

//    LocalDateTime updatedDate;


//    Long modifyUserId;


    Long createdUserId;
}
