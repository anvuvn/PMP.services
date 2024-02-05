package cs545.property.repository;

import cs545.property.domain.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ImageDataRepository extends JpaRepository<ImageData, Long> {
    Optional<ImageData> findByName(String name);
}