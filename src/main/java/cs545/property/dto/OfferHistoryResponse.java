package cs545.property.dto;

import cs545.property.constant.OfferStatus;
import cs545.property.domain.Offer;
import cs545.property.domain.OfferHistory;
import cs545.property.domain.Users;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OfferHistoryResponse {
    Long id;
//    Offer offer;
    OfferStatus status;

    LocalDateTime createdDate;

//    LocalDateTime updatedDate;

//    UserDto modifyUser;

    UserDto createdUser;

    public OfferHistoryResponse(OfferHistory o){
        id = o.getId();
        status = o.getStatus();
        createdDate = o.getCreatedDate();
//        updatedDate = o.getUpdatedDate();
//        modifyUser =new UserDto( o.getModifyUser());
        createdUser = new UserDto(o.getCreatedUser());
    }
}
