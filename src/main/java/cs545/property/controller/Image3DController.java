package cs545.property.controller;
import cs545.property.domain.ImageData;
import cs545.property.dto.ThumbsImageDataResponse;
import cs545.property.services.Image3DImageService;
import cs545.property.services.ImageDataService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UncheckedIOException;
import java.util.List;

@RestController
@RequestMapping("/image3d")
@CrossOrigin(origins = "http://localhost:3000")
public class Image3DController {

    @Autowired
    private Image3DImageService image3DService;

    @PostMapping
    public ResponseEntity<Object> uploadFiles(@RequestParam("property_id") Long property_id,  @RequestParam("file") MultipartFile file) {
        try {
            var data = image3DService.uploadPropertyImages(property_id, file);
            return new ResponseEntity<>(data, HttpStatus.OK);

        } catch (UncheckedIOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getImage3DByProperyId(@PathVariable("id") Long id) {

        try {

            var data = image3DService.getByPropertyId(id);
            return new ResponseEntity<>(data, HttpStatus.OK);

        }
        catch (UncheckedIOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}