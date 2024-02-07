package cs545.property.controller;


import cs545.property.domain.Offer;
import cs545.property.domain.Users;
import cs545.property.dto.AcceptOfferRequest;
import cs545.property.dto.request.ChangeOfferStatusRequest;
import cs545.property.dto.request.CreateOfferRequest;
import cs545.property.dto.response.GenericActivityResponse;
import cs545.property.dto.OfferDto;
import cs545.property.services.OfferService;
import cs545.property.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
    public List<OfferDto> findAll() {
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
        return offerService.create(offerRequest, propertyId);
    }

    @PutMapping("/properties/{property_id}/offers/{id}")
    public GenericActivityResponse changeStatus(HttpServletRequest req, @RequestBody ChangeOfferStatusRequest request, @PathVariable int id) {
        Users user = userService.getLoggedInUser(req);
        return offerService.changeStatus(user, id, request);
    }

    @GetMapping("/properties/{propertyId}/offers")
    public List<Offer> findPropertyOffers(@PathVariable long propertyId) {
        return offerService.findByPropertyId(propertyId);
    }

    @PostMapping("/offers/customer/{offerId}/accept")
    public GenericActivityResponse customerAcceptOffer(@PathVariable Long offerId, @RequestBody AcceptOfferRequest model) {
        try {
            return offerService.customerAcceptOffer(new AcceptOfferRequest(offerId));
        } catch (RuntimeException ex) {
            return new GenericActivityResponse(false, ex.getMessage());
        }

    }

    @PostMapping("/offers/owner/{offerId}/accept")
    public GenericActivityResponse ownerAcceptOffer(@PathVariable Long offerId, @RequestBody AcceptOfferRequest model) {
        try {
            return offerService.ownerAcceptOffer(new AcceptOfferRequest(offerId));
        } catch (RuntimeException ex) {
            return new GenericActivityResponse(false, ex.getMessage());
        }

    }

    @PostMapping("/offers/owner/{offerId}/cancel")
    public GenericActivityResponse ownerCancelOffer(@PathVariable Long offerId, @RequestBody AcceptOfferRequest model) {
        try {
            return offerService.ownerCancelOffer(new AcceptOfferRequest(offerId));
        } catch (RuntimeException ex) {
            return new GenericActivityResponse(false, ex.getMessage());
        }

    }
}
