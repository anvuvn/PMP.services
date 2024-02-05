package cs545.property.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonSerialize
public class ImageDataResponse {

    private Long id;

    private byte[] image;

    public ImageDataResponse(Long id, byte[] image){
        this.id = id;
        this.image = image;
    }

}