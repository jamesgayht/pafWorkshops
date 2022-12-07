package vttp.paf.day28.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.stereotype.Repository;

import vttp.paf.day28.models.BoardgameComments;
import vttp.paf.day28.models.Game;

@Repository
public class BGGRepo {
    
    @Autowired
    private MongoTemplate mongoTemplate; 

/*
   db.games.aggregate([
    {
        $match: {name: "Twilight Imperium"}
    },
    {
        $lookup: {from: "comments", foreignField: "gid", localField:"gid", as:"comments"}
    },
    {
        $unwind: "$comments"
    },
    {
        $sort: {"comments.rating": -1}
    },
    {
        $limit: 10
    },
    {
        $group: {_id:{name:"$name", year: "$year", image:"$image"}, 
            comments: {$push:{user:"$comments.user", rating:"$comments.rating", text:"$comments.c_text"}}}
    }
    ]);
*/

    public Optional<Game> search(String name) {
        return search(name, 10); 
    }   

    public Optional<Game> search(String name, Integer limitCount) {
    // public void search (String name, Integer limitCount) {

        // do an exact match for the BG name we are looking for
        // $match the name
        MatchOperation matchName = Aggregation.match(Criteria.where("name").is(name));
        
        // $lookup 
        LookupOperation findComments = Aggregation.lookup("comments", "gid", "gid", "comments"); 

        // $unwind
        AggregationOperation unwindComments = Aggregation.unwind("comments"); 

        // $sort 
        SortOperation sortByCommentsRating = Aggregation.sort(Sort.by(Direction.DESC, "comments.rating")); 

        // $limit
        LimitOperation limitBG = Aggregation.limit(limitCount); 

        // $project:
        // ProjectionOperation idNameRatingComments = Aggregation.project("_id", "name", "comments.rating", "comments.c_text"); 

        // $group
        GroupOperation groupByNameYearImage = Aggregation.group("name", "year", "image").push("comments").as("comments"); 

        // create the pipeline 
        Aggregation pipeline = Aggregation.newAggregation(matchName, findComments, unwindComments, sortByCommentsRating, limitBG, groupByNameYearImage); 

        // Query the collection
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, "games", Document.class); 

        List<BoardgameComments> boardgameComments = new LinkedList<>(); 

            Document document = results.iterator().next();
            Game game = Game.createGame(document); 
            boardgameComments = document.getList("comments", Document.class).stream().map(bc -> BoardgameComments.create(bc)).toList();
            game.setBgComments(boardgameComments);

            System.out.println(document.toJson());

        return Optional.of(game); 
    }
}
