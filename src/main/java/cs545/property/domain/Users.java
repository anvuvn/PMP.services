package cs545.property.domain;

import cs545.property.constant.UserRolesEnum;
import cs545.property.dto.UserRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String password;
    private Boolean isPendingApproval= true;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Role> roles= new ArrayList<>();

    public Users(UserRequest dto) {
        name = dto.getName();
    }

    public Boolean isAdmin(){
        return isInRole(UserRolesEnum.Admin);
    }

    public Boolean isInRole(UserRolesEnum role){
        return roles.stream().filter(r->r.getRole() == role).toList().size()>0;
    }

    public Boolean isOwner(){
        return isInRole(UserRolesEnum.Owner);
    }

    public Boolean isCustomer(){
        return isInRole(UserRolesEnum.Customer);
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
