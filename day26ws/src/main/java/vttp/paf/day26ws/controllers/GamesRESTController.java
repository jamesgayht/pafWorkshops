package vttp.paf.day26ws.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.paf.day26ws.models.Game;
import vttp.paf.day26ws.repositories.GameRepo;

@RestController
@RequestMapping(path="/games", produces = MediaType.APPLICATION_JSON_VALUE)
public class GamesRESTController {

    @Autowired
    private GameRepo gameRepo; 
    
    @GetMapping()
    public ResponseEntity<String> getGames() {
        return getGames(25, 0);
    }
    
    @GetMapping(params = {"limit"})
    public ResponseEntity<String> getGames(@RequestParam Integer limit) {
        return getGames(limit, 0); 
    }

    @GetMapping(params = {"limit", "skip"})
    public ResponseEntity<String> getGames(@RequestParam Integer limit, @RequestParam Integer skip) {
        
        List<Game> games = gameRepo.findGames(limit, skip); 
        Integer total = gameRepo.totalGames(); 
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 

        Long ts = System.currentTimeMillis();

        for(Game g: games) {
            System.out.println("RESTCONTROLLER >>>> " + g.toString());
            arrayBuilder.add(g.toJson()); 
        }

        JsonArray gamesArray = arrayBuilder.build(); 

        JsonObject results = Json.createObjectBuilder()
        .add("offset", skip)
        .add("limit", limit)
        .add("total", total)
        .add("timestamp", ts)
        .add("games", gamesArray)
        .build();

        return ResponseEntity.ok(results.toString()); 
    }


    @GetMapping(path = "/rank")
    public ResponseEntity<String> getGamesByRanking() {
        return getGamesByRanking(25, 0);
    }
    
    @GetMapping(path = "/rank", params = {"limit"})
    public ResponseEntity<String> getGamesByRanking(@RequestParam Integer limit) {
        return getGamesByRanking(limit, 0); 
    }

    @GetMapping(path = "/rank", params = {"limit", "skip"})
    public ResponseEntity<String> getGamesByRanking(@RequestParam Integer limit, @RequestParam Integer skip) {
        
        List<Game> games = gameRepo.findGamesByRank(limit, skip); 
        Integer total = gameRepo.totalGames(); 
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 

        Long ts = System.currentTimeMillis();

        for(Game g: games) {
            System.out.println("RESTCONTROLLER >>>> " + g.toString());
            arrayBuilder.add(g.toJson()); 
        }

        JsonArray gamesArray = arrayBuilder.build(); 

        JsonObject results = Json.createObjectBuilder()
        .add("offset", skip)
        .add("limit", limit)
        .add("total", total)
        .add("timestamp", ts)
        .add("games", gamesArray)
        .build();

        return ResponseEntity.ok(results.toString()); 
    }
    
}
