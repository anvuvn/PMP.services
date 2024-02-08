package cs545.property.repository;

import cs545.property.domain.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepo extends JpaRepository<Favourite, Long> {
    List<Favourite> findByUserId(Long userId);
    List<Favourite> findByUserIdAndPropertyId(Long userId, Long propertyId);

}
