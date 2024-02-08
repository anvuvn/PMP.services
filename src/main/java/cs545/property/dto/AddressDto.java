package cs545.property.dto;

import cs545.property.domain.Address;
import cs545.property.domain.State;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDto {

    private Integer id;

    private String line1;

    private String line2;

    private String city;

    private String postalCode;

    private String state;

    public AddressDto(Address a){
        id = a.getId();
        line1=a.getLine1();
        line2 = a.getLine2();
        city =a.getCity();
        postalCode = a.getPostalCode();
        state = a.getState();
    }
}
