package vttp.paf.day26ws.repositories;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.paf.day26ws.models.Game;
import vttp.paf.day26ws.models.GameDetails;

@Repository
public class GameRepo {

    public static final String C_GAMES = "games"; 
    public static final String C_COMMENTS = "comments"; 

    @Autowired
    private MongoTemplate mongoTemplate; 

    // db.games.find({},{name: 1, gid:1}).limit(25).skip(5);

     public List<Game> findGames(Integer limit, Integer skip) {

        // Criteria criteria = Criteria.where("name").regex("hi", "i"); 
        // Query query = Query.query(criteria); 
        // List<Document> results = mongoTemplate.find(query,Document.class, C_GAMES); 
        // System.out.println("RESULTS >>>" + results);
        
        List<Document> results = mongoTemplate.find(new Query().limit(limit).skip(skip), Document.class, C_GAMES); 

        List<Game> games = new LinkedList<>();
        
        for(Document d: results) {
            games.add(Game.createGame(d)); 
        }
        System.out.println("GAMES REPO >>>> " + games.toString());

        return games; 
     }
     
     
     //  db.games.find({},{_id: 0, name: 1, gid:1}).count();
     public Integer totalGames() {
         Integer total = (int)mongoTemplate.count(new Query(), C_GAMES); 
         System.out.println(">>>>> TOTAL: " + total);
         return total;  
        }    
        
        public List<Game> findGamesByRank(Integer limit, Integer skip) {
           
           List<Document> results = mongoTemplate.find(
                                    new Query().limit(limit).skip(skip)
                                    .with(Sort.by(Sort.Direction.ASC, "ranking")), 
                                    Document.class, C_GAMES); 
   
           List<Game> games = new LinkedList<>();
           
           for(Document d: results) {
               games.add(Game.createGame(d)); 
           }
           System.out.println("GAMES REPO >>>> " + games.toString());
   
           return games; 
        }

        public GameDetails findGamesById(Integer id) {
            
            Criteria criteria = Criteria.where("gid").is(id); 

            Query query = Query.query(criteria); 

            List<Document> results = mongoTemplate.find(query, Document.class, C_GAMES); 

            GameDetails gameDetails = new GameDetails();
            
            // for(Document d: results)
            //     gameDetails = GameDetails.createGameDetails(d); 

            Document doc = results.iterator().next(); 
            gameDetails = GameDetails.createGameDetails(doc); 

            System.out.println(gameDetails.toString());

            return gameDetails; 
        }
}
