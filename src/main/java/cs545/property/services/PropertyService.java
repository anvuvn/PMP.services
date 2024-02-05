package cs545.property.services;


import cs545.property.domain.Address;
import cs545.property.domain.Property;
import cs545.property.dto.PropertyAddRequest;
import cs545.property.dto.UserDto;
import cs545.property.repository.PropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PropertyService {


    @Autowired
    PropertyRepo propertyRepo;

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
        var p = propertyRepo.save(property);

        if (p == null)
            return null;
        return p;
    }


}
