package vttp.paf.day28ws.repositories;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import vttp.paf.day28ws.models.Comment;
import vttp.paf.day28ws.models.Games;
import vttp.paf.day28ws.models.GamesResult;

@Repository
public class GamesResultRepo {

    public static final String C_COMMENTS = "comments";
    public static final String C_GAMES = "games";

    @Autowired
    private MongoTemplate mongoTemplate;

    // db.comments.find({user: "theory"}).sort({rating: -1});

    public List<Comment> findCommentByUser(String user) {
        Criteria criteria = Criteria.where("user").is(user);
        Query query = Query.query(criteria);
        List<Document> results = mongoTemplate.find(query, Document.class, C_COMMENTS);

        List<Comment> comments = new LinkedList<>();

        for (Document d : results)
            comments.add(Comment.createComment(d));

        return comments;
    }

    /*
     * db.comments.aggregate(
     * {$match:{user:{$regex: "theo", $options:"i"}}},
     * {$lookup:{from:"games", foreignField:"gid", localField:"gid", as:"games"}},
     * {$group: {_id:{rate:"$rating", rating:"highest"} , count:{$sum:1},
     * games:{$push:"$games"}}},
     * {$project: {_id: 1, games:1}},
     * {$sort:{"_id.rate":-1}}
     * );
     */

    public GamesResult findGamesByUserAndRating(String order, String user) {
        // $match the username
        MatchOperation matchUser = Aggregation.match(Criteria.where("user").is(user));
        // MatchOperation matchUser =
        // Aggregation.match(Criteria.where("user").regex(user, "i"));

        // $lookup
        LookupOperation lookupGames = Aggregation.lookup(C_GAMES, "gid", "gid", "games");

        // $unwind
        AggregationOperation unwindGames = Aggregation.unwind("games");

        // // $group
        // GroupOperation groupByRating =
        // Aggregation.group("rating").count().as("count").push("c_text").as("comment")
        // .push("games").as("games");

        // // $project
        // ProjectionOperation ratingGamesProjection = Aggregation.project("rating",
        // "games");

        // $sort

        SortOperation sortByRating; 

        if (order.equals("highest")) {
            sortByRating = Aggregation
                    .sort(Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "rating"));
        } 
        else if(order.equals("lowest")){
            sortByRating = Aggregation
                    .sort(Sort.by(org.springframework.data.domain.Sort.Direction.ASC, "rating"));
        } 
        else {
            return null; 
        }

        // create the pipeline
        Aggregation pipeline = Aggregation.newAggregation(matchUser, lookupGames, unwindGames, sortByRating);

        // query the collection
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, C_COMMENTS, Document.class);

        System.out.println("AGGREGATION RESULTS >>>> " + results.getMappedResults());

        List<Games> games = new LinkedList<>();

        List<Document> documents = results.getMappedResults();
        // documents.add(results.iterator().next());

        for (Document d : documents) {
            // games = d.getList("games", Document.class).stream().map(g ->
            // Games.createGames(g, user)).toList();
            Document gameResult = d.get("games", Document.class);
            games.add(Games.createGames(d, gameResult));
        }

        GamesResult gamesResult = GamesResult.createGamesResult(order);
        gamesResult.setGames(games);

        return gamesResult;
    }

    /*
     * db.comments.aggregate(
     * {$lookup:{from:"games", foreignField:"gid", localField:"gid", as:"games"}},
     * {$unwind:"games"},
     * {$sort:{rating:1}},
     * {$limit:10}
     * );
     */

    public GamesResult findGamesByRatingOrder (String order) {
        // $match

        MatchOperation matchRating;

        if(order.equals("lowest")) {
            matchRating = Aggregation.match(Criteria.where("rating").is(1));
        } 
        else if(order.equals("highest")) {
            matchRating = Aggregation.match(Criteria.where("rating").is(10));
        }
        else {
            return null;
        }


        // $lookup
        LookupOperation lookupGames = Aggregation.lookup(C_GAMES, "gid", "gid", "games");

        // $unwind
        AggregationOperation unwindGames = Aggregation.unwind("games"); 

        // $sort
        SortOperation sortByRating; 

        if(order.equals("lowest")) {
            sortByRating = Aggregation.sort(Sort.by(Direction.ASC, "rating")); 
        }
        else {
            sortByRating = Aggregation.sort(Sort.by(Direction.DESC, "rating")); 
        }

        // $limit
        LimitOperation limitOperation = Aggregation.limit(10); 
        
        // create the pipeline
        Aggregation pipeline = Aggregation.newAggregation(matchRating, lookupGames, unwindGames, sortByRating, limitOperation); 

        // query the collection 
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, C_COMMENTS, Document.class); 

        System.out.println("RESULTS >>>> " + results.getMappedResults());

        List<Games> games = new LinkedList<>(); 
        List<Document> documents = results.getMappedResults(); 

        for(Document d: documents) {
            Document gameResult = d.get("games", Document.class); 
            games.add(Games.createGames(d, gameResult));
        }

        GamesResult gamesResult = GamesResult.createGamesResult(order); 
        gamesResult.setGames(games);

        return gamesResult;
    }
}
