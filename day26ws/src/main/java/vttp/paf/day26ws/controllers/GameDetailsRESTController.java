package vttp.paf.day26ws.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.paf.day26ws.models.GameDetails;
import vttp.paf.day26ws.repositories.GameRepo;

@RestController
@RequestMapping(path = "/game")
public class GameDetailsRESTController {

    @Autowired
    private GameRepo gameRepo; 
    
    @GetMapping(path = "{gid}")
    public ResponseEntity<String> getGameDetailsById(@PathVariable Integer gid) {

        GameDetails gameDetails = gameRepo.findGamesById(gid); 

        return ResponseEntity.ok(gameDetails.toJson().toString());
    }

}
