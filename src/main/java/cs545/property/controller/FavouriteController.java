package cs545.property.controller;

import cs545.property.config.UserDetailDto;
import cs545.property.dto.FavouriteCreateRequest;
import cs545.property.services.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favourites")
@CrossOrigin(origins = "http://localhost:3000")
public class FavouriteController {

    @Autowired
    FavouriteService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody FavouriteCreateRequest model) {
        var response = service.add(model);
        if (response == null) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyFavourite() {
        var user = (UserDetailDto) SecurityContextHolder.getContext().getAuthentication().getDetails();
        var userId = user.getUserId();
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @DeleteMapping("/{favId}")
    public ResponseEntity<?> deleteFav(@PathVariable Long favId) {
        try {
            if (!service.removeFavourite(favId))
                return ResponseEntity.status(404).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.status(200).build();
    }
}
