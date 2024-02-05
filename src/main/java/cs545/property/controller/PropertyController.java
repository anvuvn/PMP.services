package cs545.property.controller;

import cs545.property.domain.Property;
import cs545.property.domain.PropertyImage;
import cs545.property.dto.PropertyAddRequest;
import cs545.property.dto.PropertyImageResponse;
import cs545.property.dto.PropertyResponseDto;
import cs545.property.services.FileUploadService;
import cs545.property.services.PropertyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "properties")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5175"})
public class PropertyController {


    @Autowired
    private PropertyService propertyService;

    @GetMapping
    public List<Property> getAllProperties()
    {
        return propertyService.getAll();
    }

    @GetMapping("/{id}")
    public Property getPropertyById(@RequestParam Long id)
    {
        return propertyService.getById(id);
    }

    @PostMapping
    public PropertyResponseDto addProperty(@RequestBody PropertyAddRequest property)
    {

        return  new PropertyResponseDto(propertyService.AddProperty(property));
    }
    @GetMapping("/status/{status}")
    public List<Property> getPropertyByStatus(@PathVariable String status)
    {
        return propertyService.getAll();
    }


}
