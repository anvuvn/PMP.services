package cs545.property.controller;


import cs545.property.anotations.LogExecutionTime;
import cs545.property.dto.UserRequest;
import cs545.property.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UsersController {
    @Autowired
    UserService service;


    @PostMapping
    public ResponseEntity<?> AddUser(@RequestBody UserRequest model) {

        return ResponseEntity.ok(service.AddUser(model));

    }


    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @LogExecutionTime
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        var u = service.getById(id);
        return ResponseEntity.ok(u);
    }



    @GetMapping("/throws")
    public ResponseEntity<?> error() {
        throw new RuntimeException("This is running Test exception");
    }

}
