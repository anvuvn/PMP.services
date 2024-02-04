package cs545.property.controller;

import cs545.property.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
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
}
