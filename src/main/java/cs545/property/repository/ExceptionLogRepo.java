package cs545.property.repository;

import cs545.property.domain.ExceptionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionLogRepo extends JpaRepository<ExceptionLog, Long> {
}
