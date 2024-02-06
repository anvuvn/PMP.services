package cs545.property.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue
    private Integer id;

    private String line1;

    private String line2;

    private String city;

    private String postalCode;

    private String state;
}
