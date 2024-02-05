package cs545.property.repository;

import cs545.property.domain.Customer;
import cs545.property.domain.PropertyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    List<Customer> findTop10ByOrderByCreatedDateDesc();
}
