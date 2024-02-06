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
public class FavouriteController {

    @Autowired
    FavouriteService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody FavouriteCreateRequest model) {
        return ResponseEntity.ok(service.add(model));
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyFavourite() {
        var user = (UserDetailDto) SecurityContextHolder.getContext().getAuthentication().getDetails();
        var userId = user.getUserId();
        return ResponseEntity.ok(service.getByUserId(userId));
    }
}
