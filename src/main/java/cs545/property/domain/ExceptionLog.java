package cs545.property.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Data
@NoArgsConstructor
@Table(name = "Exception")
public class ExceptionLog {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String principle;
    private String exceptionType;
    private String operation;
}
