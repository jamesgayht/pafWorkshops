package vttp.paf.day28ws.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;
import vttp.paf.day28ws.models.Game;
import vttp.paf.day28ws.repositories.GameRepo;

@RestController 
@RequestMapping(path = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameRESTController {
    
    @Autowired
    private GameRepo gameRepo; 

    @GetMapping(path = "/{gid}/reviews")
    public ResponseEntity<String> getGameAndCommentsById(@PathVariable Integer gid) {
        
        Optional<Game> opt = gameRepo.findGameById(gid); 

        if(opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body("This game id does not exist.\n"); 
        }
        else {
            Game game = opt.get(); 
            System.out.println(">>>> GAME: " + game.toString());
            JsonObject results = game.toJson();             
            return ResponseEntity.ok(results.toString());
        }
    }

}
