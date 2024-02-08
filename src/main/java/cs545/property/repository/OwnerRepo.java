package cs545.property.repository;

import cs545.property.domain.Customer;
import cs545.property.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerRepo extends JpaRepository<Owner, Long> {


}
