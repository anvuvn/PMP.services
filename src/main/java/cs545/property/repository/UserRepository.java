package cs545.property.repository;

import cs545.property.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    public Users findByName(String name);

    List<Users> findByRolesNameEquals(String roleName);
}
