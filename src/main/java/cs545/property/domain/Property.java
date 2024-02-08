package cs545.property.domain;

import cs545.property.constant.PropertyStatus;
import cs545.property.constant.PropertyType;
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="property_id")
    List<ImageData> images;

    @Enumerated(EnumType.STRING)
    PropertyStatus status = PropertyStatus.Waiting;

    @OneToMany(mappedBy = "property")
    private List<Offer> offers;

    private Integer numberOfRoom;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="img3d_id")
    private Image3D image3d= new Image3D();

}
