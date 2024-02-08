package cs545.property.repository;

import cs545.property.domain.OfferHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferHistoryRepo extends JpaRepository<OfferHistory, Long> {

    List<OfferHistory> findByOfferId(Long propertyId);
}
