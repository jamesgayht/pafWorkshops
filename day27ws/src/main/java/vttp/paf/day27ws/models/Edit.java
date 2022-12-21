package vttp.paf.day27ws.models;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Edit {

    private String comment;
    private int rating;
    private LocalDate date;

    public static Edit createEdit(Document d) {
        Edit edit = new Edit(); 
        edit.setComment(d.getString("comment"));
        edit.setRating(d.getInteger("rating"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
        Date dateObj = d.getDate("date"); 
        edit.setDate(dateObj.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        return edit; 
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                    .add("comment", getComment())
                    .add("rating", getRating())
                    .add("date", getDate().toString())               
                    .build();
    }

    @Override
    public String toString() {
        return "Comment >>> %s, Rating >>>>> %d, Date >>>> %s\n".formatted(comment, rating, date.toString()); 
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
