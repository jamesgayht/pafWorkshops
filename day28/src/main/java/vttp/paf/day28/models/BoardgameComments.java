package vttp.paf.day28.models;

import org.bson.Document;

public class BoardgameComments {

    private String user;
    private String text;
    private Integer rating;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public static BoardgameComments create(Document d) {
        BoardgameComments boardgameComments = new BoardgameComments(); 

        boardgameComments.setUser(d.getString("user"));
        boardgameComments.setRating(d.getInteger("rating"));
        boardgameComments.setText(d.getString("c_text"));

        System.out.println("rating >>>> " + boardgameComments.getRating());

        return boardgameComments; 
    }

}
