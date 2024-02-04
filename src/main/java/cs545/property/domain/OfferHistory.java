package cs545.property.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class OfferHistory {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Offer offer;

}
