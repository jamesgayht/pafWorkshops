package vttp.paf.day28.models;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

public class Game {
    private String name;
    private String image;
    private String year; 
    private List<BoardgameComments> bgComments = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<BoardgameComments> getBgComments() {
        return bgComments;
    }

    public void setBgComments(List<BoardgameComments> bgComments) {
        this.bgComments = bgComments;
    }

    public static Game createGame(Document d) {

        Game game = new Game(); 
        Document id = d.get("_id", Document.class); 
        game.setName(id.getString("name"));
        game.setImage(id.getString("image"));

        return game; 
    }

}
