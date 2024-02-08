package cs545.property.controller;
import cs545.property.domain.ImageData;
import cs545.property.domain.Property;
import cs545.property.dto.ThumbsImageDataResponse;
import cs545.property.services.ImageDataService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/images")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageDataController {

    @Autowired
    private ImageDataService imageDataService;

    @PostMapping(value = "/upload")
    public ResponseEntity<Object> uploadFiles(@RequestParam("property_id") Long property_id,  @RequestParam("files") MultipartFile[] files) {
        try {
            var data = imageDataService.uploadPropertyImages(property_id, files);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (UncheckedIOException e) {
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<?>  getAllImagesByPropety(@PathVariable("id") String id){

        Long pid = Long.parseLong(id);
        var data = imageDataService.getImagesByPropertyId(pid);

        return ResponseEntity.status(HttpStatus.OK)
                .body(data);
    }

    @GetMapping("/thumbnail")
    public ResponseEntity<List<ThumbsImageDataResponse>> getThumbnailImages(@RequestParam("ids") Long[] ids)
    {
        var data = imageDataService.getThumbByIds(ids);
        return new ResponseEntity(data, HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?>  getAll(){
        var data = imageDataService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(data);
    }

    @GetMapping("/info/{name}")
    public ResponseEntity<?>  getImageInfoByName(@PathVariable("name") String name){
        ImageData image = imageDataService.getInfoByImageByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(image);
    }
}