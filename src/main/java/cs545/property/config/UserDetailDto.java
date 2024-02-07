package cs545.property.config;


import com.fasterxml.jackson.annotation.JsonIgnore;
import cs545.property.domain.Role;
import cs545.property.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto implements UserDetails {

    private Long userId;

    private String userName;

    private String email;

    @JsonIgnore
    private String password;

    private List<Role> roles = new ArrayList<>();


    public UserDetailDto(Users user) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.roles = user.getRoles();
        this.password = user.getPassword();
    }

    public List<Role> getRole() {
        return roles;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(String.format("%s", r.getName()))).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public boolean isAdmin() {
        return isInRole("Admin");
    }
    public boolean isOwner() {
        return isInRole("Owner");
    }   public boolean isCustomer() {
        return isInRole("Customer");
    }
    public boolean isInRole(String role){
        return roles.stream().filter(r -> r.getName().equals(role)).findFirst().isPresent();
    }

    @Override
    public String toString() {
        return "UserDetailDto{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
