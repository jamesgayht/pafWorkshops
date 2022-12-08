package vttp2022.paf.day26.repositories;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2022.paf.day26.models.TVShow;

@Repository
public class TVShowRepository {
    
    public static final String C_TV_SHOWS = "tvshows";

    @Autowired
    private MongoTemplate mongoTemplate; 

    /*
     * db.tvshows.distinct({"genres"})
     */
    public List<String> findTVShowGenres() {
        
        List<String> genres = mongoTemplate.findDistinct(new Query(), "genres", C_TV_SHOWS, String.class); 

        return genres;
    }

    /*
     * db.tvshows.find({genres: "Action"}, {name:1, summary:1, "rating.average":1, "image.original":1, _id:0}).limit(10).
     */
    public List<TVShow> findTvShowsByGenre(String genre) {
        Criteria c = Criteria.where("genres").is(genre);
        Query query = Query.query(c).limit(10).skip(0); 
        List<Document> results = mongoTemplate.find(query, Document.class, C_TV_SHOWS);

        List<TVShow> shows = new LinkedList<>();

        for(Document d: results)
            shows.add(TVShow.createShow(d)); 

        return shows; 
    }

    /*
     * db.tvshows.find({
     *  name:{
     *      $regex:".*sing.*",
     *      $options:"i"
     *  }
     * })
     */
    public List<TVShow> findTvShowsByName(String name) {
        Criteria c = Criteria.where("name").regex(name, "i"); 
        Query query = Query.query(c); 
        List<Document> results = mongoTemplate.find(query, Document.class, C_TV_SHOWS); 

        List<TVShow> shows = new LinkedList<>(); 
        for(Document d:results)
            shows.add(TVShow.createShow(d));

        return shows; 
    }


    /*
     * db.tvshows.find({language: "English"})
     */
    public List<Document> findTVShowByLanguage(String language) {

        // create a criteria/predicate(condition)
        // .where is the key you want to look, .is is the value 
        Criteria c = Criteria.where("language").is(language);

        // Query to use the criteria
        Query query = Query.query(c); 

        // must take note of your collection name (maybe set up a constant above)
        List<Document> results = mongoTemplate.find(query, Document.class, C_TV_SHOWS); 

        return results; 
    }

    /*
     *  db.tvshows.find({
     *      "rating.average": {$gte: 6},
            language: "English"
     * })
     */
    public List<Document> findTVShowsByRating (Float rating) {

        Criteria c = Criteria.where("rating.average").gte(rating)
                        .andOperator(
                            Criteria.where("language").is("English")
                    );

        Query query = Query.query(c);
        List<Document> results = mongoTemplate.find(query, Document.class, C_TV_SHOWS); 

        return results;
    }



    
}
