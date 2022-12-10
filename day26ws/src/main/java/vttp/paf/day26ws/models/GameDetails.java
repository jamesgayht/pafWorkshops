package vttp.paf.day26ws.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class GameDetails {

    private Integer id;
    private String name;
    private Integer year;
    private Integer ranking;
    private Integer usersRated;
    private String url;
    private String image;

    public JsonObject toJson() {

        return Json.createObjectBuilder()
                .add("gid", getId())    
                .add("name", getName())    
                .add("year", getYear())    
                .add("ranking", getRanking())    
                .add("users_rated", getUsersRated())    
                .add("url", getUrl())    
                .add("image", getImage())    
                .build();
    }

    public static GameDetails createGameDetails (Document d) {
        GameDetails gameDetails = new GameDetails(); 
        gameDetails.setId(d.getInteger("gid"));
        gameDetails.setName(d.getString("name"));
        gameDetails.setYear(d.getInteger("year"));
        gameDetails.setRanking(d.getInteger("ranking"));
        gameDetails.setUsersRated(d.getInteger("users_rated"));
        gameDetails.setUrl(d.getString("url"));
        gameDetails.setImage(d.getString("image"));
        return gameDetails; 
    }

    @Override
    public String toString() {
        return "ID: %d, NAME: %s, URL:%s\n".formatted(id, name, url); 
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getUsersRated() {
        return usersRated;
    }

    public void setUsersRated(Integer usersRated) {
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
}
