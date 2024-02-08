package cs545.property.dto;

import cs545.property.domain.Favourite;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
