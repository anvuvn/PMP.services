package cs545.property.domain;

import cs545.property.constant.OfferStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Offer {
    @Id
    @GeneratedValue
    Long id;
    @Enumerated
    OfferStatus status;
}


