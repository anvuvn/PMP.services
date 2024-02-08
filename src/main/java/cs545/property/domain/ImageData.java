package cs545.property.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageData {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String type;

    @Lob
    @Column(length = 1000)
    private byte[] imageData;

}