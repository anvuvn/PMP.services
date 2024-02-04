package cs545.property.repository;

import cs545.property.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,String> {
}
