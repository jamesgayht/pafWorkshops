package vttp.paf.day26ws.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Game {

    private Integer id;
    private String name;
    private Integer ranking; 

    public static Game createGame(Document d) {

        Game game = new Game();
        game.setId(d.getInteger("gid"));
        game.setName(d.getString("name"));
        game.setRanking(d.getInteger("ranking"));
        return game; 
    }

    public JsonObject toJson() {

        return Json.createObjectBuilder()
                .add("name", getName())
                .add("gid", getId())
                .add("ranking", getRanking())
                .build();
    }

    @Override
    public String toString() {
        return "NAME >>> %s, GID >>>> %d, RANKING >>>> %d\n".formatted(name, id, ranking); 
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
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



}
