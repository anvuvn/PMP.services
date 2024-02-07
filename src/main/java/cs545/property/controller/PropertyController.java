package cs545.property.controller;

import cs545.property.config.UserDetailDto;
import cs545.property.domain.Property;
import cs545.property.dto.*;
import cs545.property.services.PropertyService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/properties")
@CrossOrigin(origins ="http://localhost:3000")
public class PropertyController {


    @Autowired
    private PropertyService propertyService;

    @GetMapping
    public ResponseEntity<List<PropertyGridResponse>> getAllProperties()
    {
        return new ResponseEntity<>(propertyService.getAll().stream().map(p->new PropertyGridResponse(p)).toList(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Property>> getAll()
    {
        return new ResponseEntity<>(propertyService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyGridResponse> getPropertyById(@PathVariable Long id)
    {
        return new ResponseEntity( new PropertyGridResponse(propertyService.getById(id)), HttpStatus.OK);
    }

    @PostMapping
    public PropertyResponseDto addProperty(@RequestBody PropertyAddRequest property) throws IllegalAccessException {
        //
        var user = (UserDetailDto) SecurityContextHolder.getContext().getAuthentication().getDetails();
        var userId = user.getUserId();
        property.setOwnerId(userId);
        //
        return  new PropertyResponseDto(propertyService.AddProperty(property));
    }
    @GetMapping("/status/{status}")
    public List<PropertyResponseDto> getPropertyByStatus(@PathVariable String status)
    {
        return propertyService.getAll().stream().map(p->new PropertyResponseDto(p)).toList();
    }

    @GetMapping("/my")
    public List<PropertyResponseDto> getMyProperties()
    {
        var user = (UserDetailDto) SecurityContextHolder.getContext().getAuthentication().getDetails();
        var userId = user.getUserId();

        return propertyService.getPropertiesByOwnerId(userId);
    }
    @GetMapping("/{id}/approval")
    public ResponseEntity<?> approveProperty(@PathVariable Long id)
    {
        var user = (UserDetailDto) SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(!user.isAdmin()){
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(propertyService.approveProperty(id));
    }
    @PostMapping("/filters")
    public ResponseEntity<?> searchProperties(@RequestBody PropertySearchRequest model)
    {
        return ResponseEntity.ok(propertyService.searchProperty(model));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable Long id, @RequestBody PropertyUpdateRequest property)
    {
        var prop = propertyService.updateProperty(id, property);
        return ResponseEntity.ok(prop);
    }

}
