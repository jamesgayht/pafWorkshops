package vttp.paf.day29.models;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

public class MarvelCharacter {

    private Integer id; 
    private String name;
    private String description;
    private String image;
    private String details; 

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "MarvelCharacter {id=%d, name=%s, description=%s, image=%s, details=%s\n}".formatted(id, name, description, image, details); 
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("image", image)
                .add("details", details)
                .build();
    }

    public static MarvelCharacter fromCache (JsonObject doc) {
        MarvelCharacter marvelCharacter = new MarvelCharacter(); 

        marvelCharacter.setId(doc.getInt("id"));
        marvelCharacter.setName(doc.getString("name"));
        marvelCharacter.setDescription(doc.getString("description"));
        marvelCharacter.setImage(doc.getString("image"));
        marvelCharacter.setDetails(doc.getString("details"));

        return marvelCharacter; 
    }

    public static MarvelCharacter createJson(JsonObject o) {

        MarvelCharacter marvelCharacter = new MarvelCharacter();
        marvelCharacter.setId(o.getInt("id"));
        marvelCharacter.setName(o.getString("name"));
        marvelCharacter.setDescription(o.getString("description", "No Description"));

        JsonObject img = o.getJsonObject("thumbnail"); 
        marvelCharacter.setImage("%s.%s".formatted(img.getString("path"), img.getString("extension")));

        JsonArray urls = o.getJsonArray("urls"); 
        for(Integer i=0; i<urls.size(); i++) {

            JsonObject d = urls.getJsonObject(i); 

            // if("detail".equals(d.getString("type"))) {
            if(d.getString("type").equals("detail")) {

                marvelCharacter.setDetails(d.getString("url"));
            }
        }

        return marvelCharacter;
    }


}
