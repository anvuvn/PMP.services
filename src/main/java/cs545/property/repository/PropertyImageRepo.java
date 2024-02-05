package cs545.property.repository;

import cs545.property.domain.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyImageRepo extends JpaRepository<PropertyImage, Long> {
}
