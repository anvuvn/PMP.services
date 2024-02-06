package cs545.property.repository;

import cs545.property.domain.Property;
import cs545.property.domain.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepo extends JpaRepository<Property, Long> {

    List<Property> findByOwnerId(Long ownerId);
}
