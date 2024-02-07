package cs545.property.domain;

import cs545.property.constant.OfferStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class OfferHistory {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Offer offer;

    @Enumerated(EnumType.STRING)
    OfferStatus status;

    LocalDateTime createdDate;

    LocalDateTime updatedDate;
//
//    @ManyToOne
//    Users modifyUser;

    @ManyToOne
    Users createdUser;

}
