package vttp.paf.day28ws.models;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Game {
    private int gid;
    private String name;
    private int year;
    private int ranking;
    private int usersRated;
    private String url;
    private String image;
    List<Comment> comments = new LinkedList<>();
    
    public JsonObject toJson() {
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder(); 
        
        for(Comment c: comments) {
            arrayBuilder.add(objectBuilder.add("c_id", c.getCommentId())
                                            .add("user", c.getUser())
                                            .add("rating", c.getRating())
                                            .add("c_text", c.getCommentText())
                                            .add("gid", c.getGid())
                                            .build());
        }

        return Json.createObjectBuilder()
                .add("gid", getGid())
                .add("name", getName())
                .add("year", getYear())
                .add("ranking", getRanking())
                .add("users_rated", getUsersRated())
                .add("url", getUrl())
                .add("image", getImage())
                .add("reviews", arrayBuilder)
                .add("timestamp", LocalDateTime.now().toString())
                .build();
    }
                
            

    public static Game createGame (Document d) {

        Game game = new Game(); 
        game.setGid(d.getInteger("gid"));
        game.setName(d.getString("name"));
        game.setYear(d.getInteger("year"));
        game.setRanking(d.getInteger("ranking"));
        game.setUsersRated(d.getInteger("users_rated"));
        game.setUrl(d.getString("url"));
        game.setImage(d.getString("image"));
        return game; 
    }

    @Override
    public String toString() {
        return "GID: %d, Name: %s\n".formatted(gid, name);
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getUsersRated() {
        return usersRated;
    }

    public void setUsersRated(int usersRated) {
        this.usersRated = usersRated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
