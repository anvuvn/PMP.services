package cs545.property.domain;

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

    @OneToOne
    @JoinColumn(name = "address_id")
    Address address;

    @OneToMany
    List<PropertyImage> images;
}
