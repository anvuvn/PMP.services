package cs545.property.repository;

import cs545.property.domain.OfferHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferHistoryRepo extends JpaRepository<OfferHistory, Long> {
}
