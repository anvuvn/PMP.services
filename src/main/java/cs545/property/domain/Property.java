package cs545.property.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Property {
    @Id
    @GeneratedValue
    Long id;
    String homeType;
    String propertyType;
    Double price;
    String location;
    String name;

}
