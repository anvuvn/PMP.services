package cs545.property.services;

import cs545.property.config.JwtHelper;
import cs545.property.domain.Customer;
import cs545.property.domain.Owner;
import cs545.property.domain.Role;
import cs545.property.domain.Users;
import cs545.property.dto.UserDto;
import cs545.property.dto.UserRequest;
import cs545.property.repository.CustomerRepo;
import cs545.property.repository.OwnerRepo;
import cs545.property.repository.RoleRepo;
import cs545.property.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    OwnerRepo ownerRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtHelper jwtHelper;

    @Transactional
    public UserDto AddUser(UserRequest model) {

        var u = new Users(model);
        u.setRoles(model.getRoles().stream().map(r -> {
            var role = roleRepo.findById(r);
            if (role.isEmpty()) {
                role = Optional.of(roleRepo.save(new Role(r)));
            }

            return role.get();
        }).toList());
        u.setPassword(encoder.encode(model.getPassword()));
        if (u.isOwner()) {
            //create Owner
            u.setIsPendingApproval(true);
            var owner = new Owner();
            owner.setName(u.getName());
            ownerRepo.save(owner);
        }
        if (u.isCustomer()) {
            //create customer
            var customer = new Customer();
            customer.setName(u.getName());
            customerRepo.save(customer);
        }
        userRepo.save(u);


        return new UserDto(u);
    }

    @Transactional
    public List<UserDto> getAll() {
        return userRepo.findAll().stream().map(u -> new UserDto(u)).toList();
    }


    @Transactional
    public List<UserDto> getAllByRoleName(String roleName) {
        return userRepo.findByRolesNameEquals(roleName).stream().map(u -> new UserDto(u)).toList();
    }

    @Transactional
    public UserDto getById(Long id) {
        var u = userRepo.getReferenceById(id);

        if (u == null)
            return null;
        return new UserDto(u);
    }

    public Users getLoggedInUser(HttpServletRequest request) {
        // Get the JWT token from the Authorization header
        String token = extractTokenFromHeader(request);

        Users user = jwtHelper.getLoggedInUser(token);

        return user;
    }

    // Helper method to extract the JWT token from the Authorization header
    private String extractTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Skip "Bearer " prefix
        }

        return null;
    }




}
