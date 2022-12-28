package vttp.paf.day28ws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;
import vttp.paf.day28ws.models.GamesResult;
import vttp.paf.day28ws.repositories.GameRepo;
import vttp.paf.day28ws.repositories.GamesResultRepo;

// produces a list of games sorted by ratings 
@RestController
@RequestMapping(path = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
public class GamesRESTController {
    
    @Autowired 
    private GameRepo gameRepo; 

    @Autowired 
    private GamesResultRepo gamesResultRepo; 

    @GetMapping(path = "/highest/{user}")
    public ResponseEntity<String> getGamesByUserHighest(@PathVariable String user) {

        GamesResult gamesResult = gamesResultRepo.findGamesByUserRatingHighestToLowest(user);
        
        JsonObject results = gamesResult.toJson(); 
        return ResponseEntity.ok(results.toString());
    }

    

}
