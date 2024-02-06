package cs545.property.repository;

import cs545.property.domain.Offer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepo extends CrudRepository<Offer, Long> {
    List<Offer> findByCustomerIdAndPropertyId(long id, long propertyId);

    List<Offer> findByCustomerId(long id);

    List<Offer> findAll();

    @Query("select o from offer o where o.property.id=:propertyId and o.status='APPROVED'")
    Offer getCompletedOfferIfExists(long propertyId);

    @Query("select o.customer.name from offer o where o.property.id=:propertyId and o.property.propertyStatus='COMPLETED' and o.status='APPROVED'")
    String getEmailForCompletedCustomer(long propertyId);
}
