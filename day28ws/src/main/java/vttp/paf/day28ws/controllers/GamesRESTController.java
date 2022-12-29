package vttp.paf.day28ws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;
import vttp.paf.day28ws.models.GamesResult;
import vttp.paf.day28ws.repositories.GamesResultRepo;

// produces a list of games sorted by ratings 
@RestController
@RequestMapping(path = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
public class GamesRESTController {

    @Autowired 
    private GamesResultRepo gamesResultRepo; 

    @GetMapping(path = "/{order}/{user}")
    public ResponseEntity<String> getGamesByUserAndOrder(@PathVariable String order, @PathVariable String user) {
        
        if(gamesResultRepo.findGamesByUserAndRating(order, user) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body("Unable to process this request.");
        } 
        else {
            GamesResult gamesResult = gamesResultRepo.findGamesByUserAndRating(order, user);
            
            JsonObject results = gamesResult.toJson(); 
            return ResponseEntity.ok(results.toString());

        }
    }

    @GetMapping(path = "/{order}")
    public ResponseEntity<String> getGamesByLowest(@PathVariable String order) {

        if(gamesResultRepo.findGamesByRatingOrderProper(order) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body("Unable to process this request."); 
        }
        else {
            GamesResult gamesResult = gamesResultRepo.findGamesByRatingOrderProper(order);
            
            JsonObject results = gamesResult.toJson(); 
    
            return ResponseEntity.ok(results.toString());
        }
    }
    

}
