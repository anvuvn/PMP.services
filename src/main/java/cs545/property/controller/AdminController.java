package cs545.property.controller;

import cs545.property.dto.OfferHistoryResponse;
import cs545.property.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET,RequestMethod.PUT, RequestMethod.OPTIONS})
public class AdminController {
    @Autowired
    AdminService service;
    @GetMapping("/hello")
    public ResponseEntity helloWorld(){
        return ResponseEntity.ok("Hello-world");
    }

    @GetMapping("/dashboard")
    public  ResponseEntity<?> getDashBoard(){
        return  ResponseEntity.ok(service.getAdminDashBoard());
    }

    @PostMapping("/owners/{id}/approval")
    public  ResponseEntity<?> approveOwner(@PathVariable Long id){

        return  ResponseEntity.ok(service.approveOwner(id));
    }

    @GetMapping("/offers/history")
    public List<OfferHistoryResponse> history(){
        return service.history();
    }
}
