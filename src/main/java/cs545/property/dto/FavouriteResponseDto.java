package cs545.property.dto;

import cs545.property.domain.Favourite;
import lombok.Data;

@Data
public class FavouriteResponseDto {
    Long id;
    UserDto user;
    PropertyResponseDto property;
    public FavouriteResponseDto(Favourite f){
        id = f.getId();
        user = new UserDto(f.getUser());
        property = new PropertyResponseDto(f.getProperty());
    }
}
