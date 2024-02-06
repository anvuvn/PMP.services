package cs545.property.domain;

import cs545.property.constant.PropertyStatus;
import cs545.property.constant.PropertyTransactionStatus;
import cs545.property.domain.enums.PropertyType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Property {
    @Id
    @GeneratedValue
    Long id;

    @Enumerated(EnumType.STRING)
    PropertyType propertyType;

    Double price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    Address address;

    @ManyToOne
    Users owner;

    @OneToMany
    List<PropertyImage> images;

    @Enumerated(EnumType.STRING)
    PropertyStatus status = PropertyStatus.Waiting;

    @OneToMany(mappedBy = "property")
    private List<Offer> offers;

    @ManyToOne
    private Owner owner;

    @Enumerated(EnumType.STRING)
    private PropertyTransactionStatus propertyStatus;
}
