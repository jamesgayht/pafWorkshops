package vttp.paf.day28ws.models;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators.ToString;

public class Games {
    private int gid;
    private String name;
    private int rating;
    private String user;
    private String comment;
    private String reviewId;

    public static Games createGames(Document d) {
        Games games = new Games(); 
        games.setGid(d.getInteger("gid"));
        games.setName(d.getString("name"));
        games.setRating(d.getInteger("rating"));
        games.setUser(d.getString("user"));
        games.setComment(d.getString("c_text"));
        games.setReviewId(d.getString("c_id"));

        return games;
    }

    public static Games createGames(Document d, Document doc) {
        Games games = new Games(); 
        games.setGid(d.getInteger("gid"));
        games.setName(d.getString("name"));
        games.setRating(doc.getInteger("rating"));
        games.setUser(doc.getString("user"));
        games.setComment(doc.getString("c_text"));
        games.setReviewId(doc.getString("c_id"));

        return games;
    }

    @Override
    public String toString() {
        return "NAME >> %s, USER >>> %s, COMMENT >> %s, REVIEW_ID >> %s\n".formatted(name, user, comment, reviewId);
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }
}
