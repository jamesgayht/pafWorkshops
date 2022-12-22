package vttp.paf.day28ws.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import vttp.paf.day28ws.models.Comment;
import vttp.paf.day28ws.models.Game;

@Repository
public class GameRepo {

    public static final String C_COMMENTS = "comments";
    public static final String C_GAMES = "games";

    @Autowired
    private MongoTemplate mongoTemplate; 

    /*
     *  db.games.aggregate(
     *      {$match: {gid: 45986}},
     *      {$lookup: {from: "comments", foreignField: "gid", localField: "gid", as: "reviews"}}
     *  );
     * 
     */

    public Optional<Game> findGameById(Integer gid) {

        // $match the gid
        MatchOperation matchGid = Aggregation.match(Criteria.where("gid").is(gid)); 

        // $lookup
        LookupOperation lookupComments = Aggregation.lookup(C_COMMENTS, "gid", "gid", "reviews");

        // create the pipeline 
        Aggregation pipeline = Aggregation.newAggregation(matchGid, lookupComments); 

        // query the collection 
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, "games", Document.class); 
        
        List<Comment> comments = new LinkedList<>(); 

        if(!results.iterator().hasNext()) {
            return Optional.empty();
        } else {
            Document document = results.iterator().next(); 
            Game game = Game.createGame(document); 
            comments = document.getList("reviews", Document.class).stream().map(c -> Comment.createComment(c)).toList(); 
            game.setComments(comments);
    
            return Optional.of(game); 
        }
    }
}
