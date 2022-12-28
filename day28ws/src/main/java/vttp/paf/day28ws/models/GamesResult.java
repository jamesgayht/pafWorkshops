package vttp.paf.day28ws.models;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class GamesResult {
    
    private String ratingOrder;
    private List<Games> games = new LinkedList<>();
    private LocalDateTime timestamp;
    
    public JsonObject toJson() {

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder(); 

        for(Games g: games) {
            arrayBuilder.add(objectBuilder.add("gid", g.getGid())
                                            .add("name", g.getName())
                                            .add("rating", g.getRating())
                                            .add("user", g.getUser())
                                            .add("comment", g.getComment())
                                            .add("review_id", g.getReviewId())
                                            .build());
        }

        return Json.createObjectBuilder()
                    .add("rating", ratingOrder)
                    .add("games", arrayBuilder)
                    .add("timestamp", timestamp.toString())
                    .build(); 
    }

    public static GamesResult createGamesResult (String order) {
        GamesResult gamesResult = new GamesResult(); 
        gamesResult.setRatingOrder(order);
        gamesResult.setTimestamp(LocalDateTime.now());
        return gamesResult; 
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getRatingOrder() {
        return ratingOrder;
    }

    public void setRatingOrder(String ratingOrder) {
        this.ratingOrder = ratingOrder;
    }

    public List<Games> getGames() {
        return games;
    }

    public void setGames(List<Games> games) {
        this.games = games;
    }

}
