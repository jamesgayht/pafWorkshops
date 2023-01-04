package vttp.paf.day28ws.repositories;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
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
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;

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
        } else if (order.equals("lowest")) {
            sortByRating = Aggregation
                    .sort(Sort.by(org.springframework.data.domain.Sort.Direction.ASC, "rating"));
        } else {
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
     * db.games.aggregate(
     * {$match: {name:{$regex: "kill", $options:"i"}}},
     * {$lookup: {from:"comments", foreignField: "gid", localField:"gid",
     * as:"reviews",
     * pipeline:[
     * {$sort:{rating:-1}},
     * {$limit:1}
     * ]}
     * },
     * {$unwind:"$reviews"},
     * {$project: {_id: 0, _id:"$gid", name:1, rating:"$reviews.rating", user:
     * "$reviews.user", comment:"$reviews.c_text", review_id:"$reviews.c_id"}},
     * {$sort:{name:1}},
     * {$limit:10}
     * );
     * 
     */

    public GamesResult findGamesByRatingOrderProper(String order) {
        // $match
        MatchOperation matchName = Aggregation.match(Criteria.where("name").regex("kill", "i"));

        // List<? extends Bson> lookupPipeline =
        // Arrays.asList(Aggregates.sort(Sorts.descending("rating")),
        // Aggregates.limit(1));

        // Bson reviews = Aggregates.lookup(C_COMMENTS, lookupPipeline,"reviews");

        // $lookup
        LookupOperation lookupReviews = Aggregation.lookup(C_COMMENTS, "gid", "gid", "reviews");

        // String query = "{$lookup: {from:\"comments\", foreignField: \"gid\",
        // localField:\"gid\", as:\"review\",
        // pipeline:[{$sort:{rating:-1}},{$limit:1}]}}";

        // $unwind
        AggregationOperation unwindReviews = Aggregation.unwind("reviews");

        // $sort
        SortOperation sortByRating;

        if (order.equals("lowest")) {
            sortByRating = Aggregation.sort(Sort.by(Direction.ASC, "reviews.rating"));
        } else {
            sortByRating = Aggregation.sort(Sort.by(Direction.DESC, "reviews.rating"));
        }

        // $limit
        LimitOperation limitOperation = Aggregation.limit(10);

        // create the pipeline
        Aggregation pipeline = Aggregation.newAggregation(matchName, lookupReviews, unwindReviews, sortByRating,
                limitOperation);

        // TODO not working
        // TypedAggregation<Document> aggregation =
        // Aggregation.newAggregation(Document.class, matchName,
        // new CustomProjectAggregationOperation(query), unwindReviews, sortByRating,
        // limitOperation);

        // query the collection
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, C_GAMES, Document.class);

        // AggregationResults<Document> results = mongoTemplate.aggregate(aggregation,
        // C_GAMES, Document.class);

        System.out.println("RESULTS >>> " + results.getMappedResults());

        List<Games> games = new LinkedList<>();
        List<Document> documents = results.getMappedResults();

        for (Document d : documents) {
            Document reviewResult = d.get("reviews", Document.class);
            games.add(Games.createGames(d, reviewResult));
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

    public GamesResult findGamesByRatingOrder(String order) {
        // $match

        // MatchOperation matchRating;

        // if(order.equals("lowest")) {
        // matchRating = Aggregation.match(Criteria.where("rating").is(1));
        // }
        // else if(order.equals("highest")) {
        // matchRating = Aggregation.match(Criteria.where("rating").is(10));
        // }
        // else {
        // return null;
        // }

        // $lookup
        LookupOperation lookupGames = Aggregation.lookup(C_GAMES, "gid", "gid", "games");

        // $unwind
        AggregationOperation unwindGames = Aggregation.unwind("games");

        // $sort
        SortOperation sortByRating;

        if (order.equals("lowest")) {
            sortByRating = Aggregation.sort(Sort.by(Direction.ASC, "rating"));
        } else {
            sortByRating = Aggregation.sort(Sort.by(Direction.DESC, "rating"));
        }

        // $limit
        LimitOperation limitOperation = Aggregation.limit(50);

        // create the pipeline
        Aggregation pipeline = Aggregation.newAggregation(lookupGames, unwindGames, sortByRating, limitOperation);

        // query the collection
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, C_COMMENTS, Document.class);

        System.out.println("RESULTS >>>> " + results.getMappedResults());

        List<Games> games = new LinkedList<>();
        List<Document> documents = results.getMappedResults();

        for (Document d : documents) {
            Document gameResult = d.get("games", Document.class);
            games.add(Games.createGames(d, gameResult));
        }

        GamesResult gamesResult = GamesResult.createGamesResult(order);
        gamesResult.setGames(games);

        return gamesResult;
    }

    /*
     * db.comments.aggregate(
     * {$sort:{gid: 1, rating: -1}},
     * {$group:{_id:"$gid", best_rating:{$first:"$$ROOT"}}},
     * {$replaceWith:"$best_rating"},
     * {$sort:{gid:1}},
     * {$lookup: {from:"games", foreignField:"gid", localField:"gid", as:"games"}},
     * {$unwind:"$games"},
     * {$project:{_id:0, _id:"$gid", name:"$games.name", rating:1, user:1,
     * comment:"$c_text", review_id:"c_id"}}
     * );
     */

    public GamesResult findGamesByRatingOrderFinal(String order) {
        
        // $sort
        SortOperation sortByRating;
        
        if(order.equals("highest")) {
            sortByRating = Aggregation.sort(Sort.by(Direction.DESC, "rating").and(Sort.by(Direction.ASC, "gid")));
        }
        else if(order.equals("lowest")) {
            sortByRating = Aggregation.sort(Sort.by(Direction.ASC, "rating")).and(Sort.by(Direction.ASC, "gid"));
        } 
        else {
            System.out.println("Something went wrong in the if clause P1");
            return null;
        }

        GroupOperation groupByGame; 
        if(order.equals("highest")) {
            groupByGame = Aggregation.group("gid")
                                                        .first("$$ROOT")
                                                        .as(order);
        } 
        else if (order.equals("lowest")) {
            groupByGame = Aggregation.group("gid").first("$$ROOT").as(order);
        }
        else {
            System.out.println("Something went wrong in the if clause P2");
            return null;
        }

        SortOperation sortByGid = Aggregation.sort(Sort.by(Direction.ASC, "gid"));

        LookupOperation lookupGames = Aggregation.lookup(C_GAMES, "gid", "gid", C_GAMES);

        AggregationOperation unwindGames = Aggregation.unwind("games");

        ProjectionOperation selectFields = Aggregation
                                            .project("rating", "user")
                                            .and("gid").as("gid")
                                            .and("games.name").as("name")
                                            .and("c_text").as("c_text")
                                            .and("c_id").as("c_id")
                                            .andExclude("_id");

        Aggregation pipeline = Aggregation.newAggregation(sortByRating, groupByGame, sortByGid, lookupGames, unwindGames, selectFields); 

        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, C_COMMENTS, Document.class);

        System.out.println("RESULTS >>>> " + results.getMappedResults());

        List<Games> games = new LinkedList<>();
        List<Document> documents = results.getMappedResults(); 

        for(Document d: documents) {
            games.add(Games.createGames(d));
        }

        GamesResult gamesResult = GamesResult.createGamesResult(order);
        gamesResult.setGames(games);

        return gamesResult; 
    }

}
