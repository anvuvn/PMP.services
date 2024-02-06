package cs545.property.controller;

import cs545.property.config.JwtHelper;
import cs545.property.config.UserDetailDto;
import cs545.property.domain.Users;
import cs545.property.dto.UserDto;
import cs545.property.dto.request.ChangeOfferStatusRequest;
import cs545.property.dto.request.CreateOfferRequest;
import cs545.property.dto.response.GenericActivityResponse;
import cs545.property.dto.OfferDto;
import cs545.property.services.OfferService;
import cs545.property.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class OfferController {

    private final OfferService offerService;
    private final UserService userService;

    @GetMapping("/offers")
    public List<OfferDto> findAll(){
        return offerService.findAll();
    }

    @GetMapping("/offers/user")
    public List<OfferDto> findAllForUser(HttpServletRequest request) {

        Users user = userService.getLoggedInUser(request);

        var userId = user.getId();

        if (userId != null) {
            return offerService.findByCustomerId(userId);
        }

        return new ArrayList<>();
    }

    @PostMapping("/properties/{propertyId}/offers")
    public GenericActivityResponse save(HttpServletRequest request, @RequestBody CreateOfferRequest offerRequest, @PathVariable long propertyId) {
        Users user = userService.getLoggedInUser(request);
        return offerService.create(user, offerRequest, propertyId);
    }

    @PutMapping("/properties/{property_id}/offers/{id}")
    public GenericActivityResponse changeStatus(HttpServletRequest req, @RequestBody ChangeOfferStatusRequest request, @PathVariable int id) {
        Users user = userService.getLoggedInUser(req);
        return offerService.changeStatus(user, id, request);
    }

    @GetMapping("/properties/{propertyId}/offers")
    public List<OfferDto> findPropertyOffers(@PathVariable long propertyId) {
        return offerService.findByPropertyId(propertyId);
    }
}
