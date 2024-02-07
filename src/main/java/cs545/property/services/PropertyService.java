package cs545.property.services;


import cs545.property.constant.PropertyStatus;
import cs545.property.domain.Address;
import cs545.property.domain.Property;
import cs545.property.dto.PropertyAddRequest;
import cs545.property.dto.PropertyResponseDto;
import cs545.property.dto.PropertySearchRequest;
import cs545.property.dto.UserDto;
import cs545.property.repository.PropertyRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import cs545.property.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@Transactional
public class PropertyService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    PropertyRepo propertyRepo;


    @Autowired
    UserRepository userRepo;

    @Transactional
    public List<Property> getAll() {
        return propertyRepo.findAll().stream().toList();
    }

    @Transactional
    public Property getById(Long id) {
        var p = propertyRepo.getReferenceById(id);

        if (p == null)
            return null;

        return p;
    }

    @Transactional
    public Property SaveProperty(Property property) {
        var p = propertyRepo.save(property);

        if (p == null)
            return null;
        return p;
    }

    @Transactional
    public Property AddProperty(PropertyAddRequest model) {
        var property = new Property();
        property.setPropertyType(model.getPropertyType());
        property.setAddress(new Address(model.getAddress()));
        property.setPrice(model.getPrice());
        property.setOwner(userRepo.getReferenceById(model.getOwnerId()));
        var p = propertyRepo.save(property);

        if (p == null)
            return null;
        return p;
    }


    @Transactional
    public List<PropertyResponseDto> getPropertiesByOwnerId(Long ownerId) {
        return propertyRepo.findByOwnerId(ownerId).stream().map(p -> new PropertyResponseDto(p)).toList();
    }

    public PropertyResponseDto approveProperty(Long id) {
        var p = propertyRepo.getReferenceById(id);
        p.setStatus(PropertyStatus.Available);
        propertyRepo.save(p);
        return new PropertyResponseDto(p);
    }

    public List<PropertyResponseDto> searchProperty(PropertySearchRequest model) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(Property.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        var root = query.from(Property.class);
        var addressJoin = root.join("address");
        if (model.getMinPrice() > 0) {
            var p = builder.greaterThan(root.get("price"), model.getMinPrice());
            predicates.add(p);
        }
        if (model.getMaxPrice() > 0) {
            var p = builder.lessThan(root.get("price"), model.getMaxPrice());
            predicates.add(p);
        }
        if (model.getPropertyType() != null && model.getPropertyType() != "") {
            var p = builder.equal(root.get("propertyType"), model.getPropertyType());
            predicates.add(p);
        }
        if (model.getLocation() != null && model.getLocation() != "") {
            var locationParts = Arrays.stream(model.getLocation().split("[||]")).filter(x->!x.isEmpty()).toArray();
            var state = locationParts[0].toString();
            var city = locationParts[1].toString();
            var p = builder.like(addressJoin.get("city"), city);
            var p1 = builder.like(addressJoin.get("state"), state);
            predicates.add(p);
            predicates.add(p1);
        }
        if (model.getNumberOfRoom() > 0) {
            var p = builder.equal(root.get("numberOfRoom"), model.getNumberOfRoom());
            predicates.add(p);
        }

        var criteria = builder.and(predicates.toArray(new Predicate[0]));
        query.where(criteria);
        var result = entityManager.createQuery(query).getResultList();
        return result.stream().map(p -> new PropertyResponseDto(p)).toList();
    }
}
