package cs545.property.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericActivityResponse {
    public boolean success;
    public String message;
    @JsonIgnore
    private Object data;

    public GenericActivityResponse(Boolean result,String message){
        success = result;
        this.message = message;
    }
}