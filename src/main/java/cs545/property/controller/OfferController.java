package cs545.property.controller;


import cs545.property.config.UserDetailDto;
import cs545.property.domain.Offer;
import cs545.property.domain.Users;
import cs545.property.dto.AcceptOfferRequest;
import cs545.property.dto.CustomerOfferListDto;
import cs545.property.dto.request.ChangeOfferStatusRequest;
import cs545.property.dto.request.CreateOfferRequest;
import cs545.property.dto.response.GenericActivityResponse;
import cs545.property.dto.OfferDto;
import cs545.property.repository.CustomerRepo;
import cs545.property.services.OfferService;
import cs545.property.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final CustomerRepo customerRepo;

    @GetMapping("/offers")
    public List<OfferDto> findAll() {
        return offerService.findAll();
    }

    @GetMapping("/offers/user")
    public List<OfferDto> findAllForUser(HttpServletRequest request) {

        var user = (UserDetailDto)SecurityContextHolder.getContext().getAuthentication().getDetails();
        var userId = user.getUserId();

        if (userId != null) {
            return offerService.findByCustomerId(userId);
        }

        return new ArrayList<>();
    }

    @PostMapping("/properties/{propertyId}/offers")
    public GenericActivityResponse save(@RequestBody CreateOfferRequest offerRequest, @PathVariable("propertyId") long propertyId) {
        return offerService.create(offerRequest, propertyId);
    }

    @PostMapping("/offers/{id}/accept")
    public ResponseEntity<?> acceptOffer(@PathVariable("id") Long id){
        return  ResponseEntity.ok(offerService.acceptOffer(id));
    }

    @PostMapping("/offers/{id}/cancel")
    public ResponseEntity<?> cancelOffer(@PathVariable("id") Long id){
        return  ResponseEntity.ok(offerService.cancelOffer(id));
    }

    @PutMapping("/properties/{property_id}/offers/{id}")
    public GenericActivityResponse changeStatus(@RequestBody ChangeOfferStatusRequest request, @PathVariable int id) {
        var user = (Users) SecurityContextHolder.getContext().getAuthentication().getDetails();
        var userId = user.getId();
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

    @PostMapping("/offers/owner/{offerId}/accept-after-customer")
    public GenericActivityResponse ownerAcceptOfferAfterCustomer(@PathVariable Long offerId, @RequestBody AcceptOfferRequest model) {
        try {
            return offerService.ownerAcceptOfferAfterCustomer(new AcceptOfferRequest(offerId));
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
