package cs545.property.controller;

import cs545.property.config.JwtHelper;
import cs545.property.config.UserDetailDto;
import cs545.property.dto.SendMessageRequestModel;
import cs545.property.services.MessageService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
//@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {
    @Autowired
    JwtHelper helper;
    @Autowired
    MessageService service;

    @GetMapping("/my")
    public ResponseEntity<?> getMymessage(){
        var user = (UserDetailDto)SecurityContextHolder.getContext().getAuthentication().getDetails();
        var userId = user.getUserId();
        return ResponseEntity.ok(service.getMessageByReceiver(userId));
    }

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody SendMessageRequestModel model){
        var user = (UserDetailDto)SecurityContextHolder.getContext().getAuthentication().getDetails();
        var userId = user.getUserId();
        model.setSenderId(userId);
        return ResponseEntity.ok(service.sendMessage(model));
    }
}
