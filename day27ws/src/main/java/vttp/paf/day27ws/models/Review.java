package vttp.paf.day27ws.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

// import com.google.gson.Gson;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;


public class Review { 

    private String user;
    private int rating;
    private String comment;
    private int gid;
    private LocalDate date;
    private String name;
    private List<Edit> edited = new LinkedList<>();  

    public JsonObject toJson() {
        
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for(Edit e: edited) {
            arrayBuilder.add(objectBuilder.add("comment", e.getComment())
                                            .add("rating", e.getRating())
                                            .add("posted", e.getDate().toString()));
        }

        return Json.createObjectBuilder()
                .add("user", getUser())
                .add("rating", getRating())
                .add("comment", getComment())
                .add("gid", getGid())
                .add("posted", getDate().toString())
                .add("name", getName())
                .add("edited", arrayBuilder)
                .add("timestamp", LocalDateTime.now().toString())
                .build(); 
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Edit> getEdited() {
        return edited;
    }

    public void setEdited(List<Edit> edited) {
        this.edited = edited;
    }
    
}
