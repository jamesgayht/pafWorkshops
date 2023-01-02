package vttp.paf.pafMockAssessment.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;
import vttp.paf.pafMockAssessment.models.User;
import vttp.paf.pafMockAssessment.repositories.UserRepository;
import vttp.paf.pafMockAssessment.services.UserService;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRESTController {

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private UserService userService; 
    
    @GetMapping(path = "/{userId}")
    public ResponseEntity<String> getUserByUserId(@PathVariable String userId) {

        Optional<User> opt = userService.findUserByUserId(userId);
        
        if(opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body("This user ID does not exist."); 
        }
        else {
            User user = opt.get();
            System.out.println("USER >>> " + user.toString());
            JsonObject results = user.toJson(); 
            return ResponseEntity.ok(results.toString());
        }

    } 

    @PostMapping(path = "/{username}/{name}")
    public ResponseEntity<String> postUser (@PathVariable String username, @PathVariable String name) {

        String user_id = UUID.randomUUID().toString().substring(0, 8);
        
        User user = new User(); 
        user.setUserId(user_id);
        user.setUsername(username);
        user.setName(name);

        try {
            if(userService.insertUser(user) == null) {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).contentType(MediaType.APPLICATION_JSON).body("ERROR! User was not created. "); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("ERROR PAGE.");
        }

        JsonObject results = user.toJson(); 

        return ResponseEntity.ok(results.toString()); 

    }


}
