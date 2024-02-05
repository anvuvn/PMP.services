package cs545.property.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class State {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique=true)
    private String code;

    private String name;
}
