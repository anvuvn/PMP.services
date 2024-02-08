package cs545.property.services;


import cs545.property.config.UserDetailDto;
import cs545.property.constant.PropertyStatus;
import cs545.property.domain.Address;
import cs545.property.domain.Property;
import cs545.property.dto.*;
import cs545.property.repository.PropertyRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import cs545.property.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return propertyRepo.findAll().stream().filter(p -> !p.getStatus().equals(PropertyStatus.Deleted)).toList();
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
        property.setNumberOfRoom(model.getNumberOfRoom());
        property.setOwner(userRepo.getReferenceById(model.getOwnerId()));
        property.setStatus(PropertyStatus.Waiting);

        var p = propertyRepo.save(property);
        if (p == null)
            return null;
        //
        return p;
    }

    @Transactional
    public List<PropertyResponseDto> getPropertiesByOwnerId(Long ownerId) {

        //for admin only
        //.filter(p -> !p.getStatus().equals(PropertyStatus.Deleted) && !p.getStatus().equals(PropertyStatus.Waiting) )
        return propertyRepo.findByOwnerId(ownerId).stream().filter(p -> !p.getStatus().equals(PropertyStatus.Deleted)).map(p -> new PropertyResponseDto(p)).toList();
    }

    public PropertyResponseDto approveProperty(Long id) {
        return changePropertyStatus(id, PropertyStatus.Available);
    }

    public PropertyResponseDto changePropertyStatus(Long id, PropertyStatus status) {
        var p = propertyRepo.getReferenceById(id);
        p.setStatus(status);
        propertyRepo.save(p);
        return new PropertyResponseDto(p);
    }

    public List<PropertyGridResponse> searchProperty(PropertySearchRequest model) {
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
            var locationParts = Arrays.stream(model.getLocation().split("[||]")).filter(x -> !x.isEmpty()).toArray();
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
        predicates.add(builder.not(root.get("status").in(PropertyStatus.Deleted, PropertyStatus.Waiting)));
        var criteria = builder.and(predicates.toArray(new Predicate[0]));
        query.where(criteria);
        var result = entityManager.createQuery(query).getResultList();
        return result.stream().map(p -> new PropertyGridResponse(p)).toList();
    }

    public PropertyResponseDto updateProperty(Long id, PropertyUpdateRequest model) {
        try {
            var property = propertyRepo.getReferenceById(id);
            if (property == null) {
                throw new Exception("Property does not exist");
            }
            var user = (UserDetailDto) SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (!user.getUserId().equals(model.getOwnerId())) {
                throw new Exception("Only owner can update property");
            }
            property.setOwner(userRepo.getReferenceById(model.getOwnerId()));
            //
            property.setPropertyType(model.getPropertyType());
            property.setPrice(model.getPrice());
            property.setNumberOfRoom(model.getNumberOfRoom());
            //
            property.setAddress(new Address(model.getAddress()));
            //
            var p = propertyRepo.save(property);
            return new PropertyResponseDto(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
