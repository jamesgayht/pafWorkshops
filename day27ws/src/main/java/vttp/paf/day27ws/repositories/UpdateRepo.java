package vttp.paf.day27ws.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.paf.day27ws.models.Edit;

@Repository
public class UpdateRepo {

    public static final String C_GAMES = "games"; 
    public static final String C_COMMENTS = "comments"; 
    public static final String C_REVIEWS = "reviews"; 
    
    @Autowired
    private MongoTemplate mongoTemplate; 

    public List<Edit> getUpdatesById (String reviewId) {
        
        Integer gid = Integer.parseInt(reviewId);
        Criteria criteria = Criteria.where("gid").is(gid); 
        Query query = Query.query(criteria);
        
        List<Document> results = mongoTemplate.find(query, Document.class, C_REVIEWS); 

        Document doc = results.iterator().next(); 
        
        // List<Update> updates = doc.getList("edited", D)
        return null; 
    }
    
}
