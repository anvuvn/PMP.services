package cs545.property.controller;

import cs545.property.dto.LoginRequest;
import cs545.property.dto.UserRequest;
import cs545.property.services.LoginService;
import cs545.property.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    LoginService service;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest model) {
        var response = service.login(model);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/registers")
    public ResponseEntity<?> register(@RequestBody UserRequest model) {
        return ResponseEntity.ok(userService.AddUser(model));
    }
}
