package cs545.property.repository;

import cs545.property.domain.PropertyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyTransactionRepo extends JpaRepository<PropertyTransaction, Long> {

    List<PropertyTransaction> findTop10ByOrderByTransactionDateDesc();
}
