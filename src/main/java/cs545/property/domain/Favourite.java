package cs545.property.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.User;

@Data
@Entity
public class Favourite {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Property property;

    @ManyToOne
    Users user;
}
