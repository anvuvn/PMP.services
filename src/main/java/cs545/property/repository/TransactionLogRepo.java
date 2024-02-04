package cs545.property.repository;

import cs545.property.domain.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLogRepo extends JpaRepository<TransactionLog, Long> {
}
