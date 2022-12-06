package vttp.paf.day27.repositories;

import java.util.Date;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LogRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate; 

    public void log(String keyword, Double score) {
        Document search = new Document(); 
        search.put("keyword", keyword);
        search.put("score", score); 
        search.put("date", new Date());

        mongoTemplate.insert(search, "logs");
    }
}
