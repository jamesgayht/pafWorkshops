package vttp.paf.day27.models;

import org.bson.Document;

public class Comment {

    private String user;
    private int rating;
    private String cText;
    private double score;

    public Comment () {}

    public Comment(String user, int rating, String cText, double score) {
        this.user = user; 
        this.rating = rating;
        this.cText = cText; 
        this.score = score; 
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
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

    public String getcText() {
        return cText;
    }

    public void setcText(String cText) {
        this.cText = cText;
    }

    public static Comment createComment(Document d) {

        Comment comment = new Comment(); 
        comment.setUser(d.getString("user"));
        comment.setRating(d.getInteger("rating"));
        comment.setcText(d.getString("c_text"));
        comment.setScore(d.getDouble("score"));

        return comment; 
    }

}
