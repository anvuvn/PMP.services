package cs545.property.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Message {

    @Id
    @GeneratedValue
    Long id;

    String message;

    @ManyToOne
    Users sender;

    @ManyToOne
    Users receiver;

    LocalDateTime createdDate = LocalDateTime.now();



}
