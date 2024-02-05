package cs545.property.dto;

import cs545.property.domain.Users;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String name;

    private List<String> roles = new ArrayList<>();

    private boolean isPending;
    public UserDto(Users user){
        id= user.getId();
        name = user.getName();

        roles = user.getRoles().stream().map(r->r.getName()).toList();
        isPending = user.getIsPendingApproval();

    }
    public UserDto(){}
}
