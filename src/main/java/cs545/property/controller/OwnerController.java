package cs545.property.controller;

import cs545.property.constant.OfferStatus;
import cs545.property.domain.Property;
import cs545.property.dto.AcceptOfferRequest;
import cs545.property.dto.CustomerOfferListDto;
import cs545.property.dto.response.GenericActivityResponse;
import cs545.property.services.OfferService;
import cs545.property.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/owners")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5175"})
public class OwnerController {

    @Autowired
    OwnerService ownerService;

    @Autowired
    OfferService offerService;
//    @GetMapping("/{propertyId}")
//    public List<CustomerOfferListDto> getAllPropertyOffers(@PathVariable("propertyId") long propertyId)
//    {
//        System.out.println("Testing");
//        //return ownerService.getAllCustomerOffers(id);
//        return  offerService.findCustomerOffersByPropertyId(propertyId);
//    }

    @GetMapping("/properties/{propertyId}/offers")
    public List<CustomerOfferListDto> ownerlistOfferForPropertyList(@PathVariable long propertyId) {
        return offerService.ownerGetPropertyOffers(propertyId);
    }

    @GetMapping("/offers/status/{status}")
    public  List<CustomerOfferListDto> getMyOfferByStatus(@PathVariable OfferStatus status) {

            return offerService.ownerGetMyOfferByStatus(status);

    }
}
