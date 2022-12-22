package vttp.paf.day28ws.models;

import org.bson.Document;

public class Comment {
    private String commentId;
    private String user;
    private int rating;
    private String commentText;
    private int gid;

    public static Comment createComment(Document d) {
        Comment comment = new Comment(); 
        comment.setCommentId(d.getString("c_id"));
        comment.setUser(d.getString("user"));
        comment.setRating(d.getInteger("rating"));
        comment.setCommentText(d.getString("c_text"));
        comment.setGid(d.getInteger("gid"));
        return comment; 
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
}
