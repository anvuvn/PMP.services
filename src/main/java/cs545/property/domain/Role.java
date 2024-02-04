package cs545.property.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cs545.property.constant.UserRolesEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<Users> users = new ArrayList<>();

    @Enumerated
    private UserRolesEnum role;
    public Role(String name) {
        this.name = name;
       switch (name){
           case "Admin":
               role = UserRolesEnum.Admin;
               break;
           case "Owner":
               role = UserRolesEnum.Owner;
               break;
           case "Customer":
               role = UserRolesEnum.Customer;
               break;
           default:
               throw new RuntimeException("Role not support for user:" + name);
       }
    }
}
