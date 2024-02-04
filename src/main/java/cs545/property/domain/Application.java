package cs545.property.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Application {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Users submittedUser;

    LocalDate submissionDate;

    String location;
}
