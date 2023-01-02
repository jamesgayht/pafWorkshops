package vttp.paf.pafMockAssessment.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class User {

    private String userId;
    private String username;
    private String name;

    public static User createUser (SqlRowSet rs) {
        User user = new User(); 
        user.setUserId(rs.getString("user_id"));
        user.setUsername(rs.getString("username"));
        user.setName(rs.getString("name"));
        return user; 
    }

    public JsonObject toJson() {
        if(getName() == null) {
            return Json.createObjectBuilder()
                    .add("user_id", getUserId())
                    .add("username", getUsername())
                    .build();
        }
        else {
            return Json.createObjectBuilder()
                    .add("user_id", getUserId())
                    .add("username", getUsername())
                    .add("name", getName())
                    .build();
        }
    }

    @Override
    public String toString() {
        return "USER_ID >>>> %s, username >>> %s, name >>>> %s\n".formatted(userId, username, name);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
