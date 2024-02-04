package cs545.property.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRequest {
    private Long id;
    private String name;
    private String password;
    private List<String> roles= new ArrayList<>();
}
