package vttp2022.paf.day26.models;

import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class TVShow {

    private String name;
    private String summary;
    private String image;
    private String rating;

    public TVShow () {}

    public TVShow(String name, String summary, String image, String rating) {
        this.name = name; 
        this.summary = summary;
        this.image = image; 
        this.rating = rating; 
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public static TVShow createShow(Document d) {
        TVShow tvShow = new TVShow(); 
        tvShow.setName(d.getString("name"));
        tvShow.setSummary(d.getString("summary"));
        
        Document imageDoc = d.get("image", Document.class); 
        tvShow.setImage(imageDoc.get("original").toString());

        Document ratingDoc = d.get("rating", Document.class);
        tvShow.setRating(ratingDoc.get("average").toString());

        return tvShow;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("name", getName())
                .add("summary", getSummary())
                .add("image", getImage())
                .add("rating", getRating())
                .build();
    }

    private <T> T defaultValue (T value, T defaultValue) {
        if(null != value)
            return value; 
        return defaultValue; 
    }
}
