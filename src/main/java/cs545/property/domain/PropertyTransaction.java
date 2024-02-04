package cs545.property.domain;

import cs545.property.constant.PropertyTransactionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class PropertyTransaction {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Property property;

    @Enumerated
    PropertyTransactionStatus status;

    LocalDate transactionDate;

    @ManyToOne
    Owner owner;

    @ManyToOne
    Customer customer;

    Double price;

}
