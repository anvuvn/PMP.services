package cs545.property.repository;

import cs545.property.domain.Image3D;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Image3DRepository extends JpaRepository<Image3D, Long> {

}