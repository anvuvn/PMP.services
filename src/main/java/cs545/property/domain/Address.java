package cs545.property.domain;

import cs545.property.dto.AddressDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    private Integer id;

    private String line1;

    private String line2;

    private String city;

    private String postalCode;

    private String state;

    public Address(AddressDto a){
        id = a.getId();
        line1=a.getLine1();
        line2 = a.getLine2();
        city =a.getCity();
        postalCode = a.getPostalCode();
        state = a.getState();
    }
}
