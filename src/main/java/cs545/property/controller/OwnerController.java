package cs545.property.controller;

import cs545.property.domain.Property;
import cs545.property.dto.CustomerOfferListDto;
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

    @GetMapping("/{id}")
    public List<CustomerOfferListDto> getAllCustomerOffers(@PathVariable("id") long id)
    {
        System.out.println("Testing");
        //return ownerService.getAllCustomerOffers(id);
        return  ownerService.getCustomerOffersByProperty(id);
    }

}
