package vttp.paf.day27ws.repositories;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.data.mongodb.core.query.Update;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.paf.day27ws.models.Edit;
import vttp.paf.day27ws.models.Review;

@Repository
public class ReviewsRepo {

    public static final String C_GAMES = "games";
    public static final String C_COMMENTS = "comments";
    public static final String C_REVIEWS = "reviews";

    @Autowired
    private MongoTemplate mongoTemplate;

    /*
     * db.games.find(
     * {gid: 5}, {_id:0, name:1}
     * );
     */

    public String getGameById(Integer gid) {

        System.out.println("GID >>>> " + gid);
        Criteria criteria = Criteria.where("gid").is(gid);
        Query query = Query.query(criteria);

        List<Document> results = mongoTemplate.find(query, Document.class, C_GAMES);

        if (!results.iterator().hasNext()) {
            return null;
        } else {
            Document doc = results.iterator().next();
            String name = doc.getString("name");

            return name;
        }

    }

    /*
     * db.reviews.insertOne({
     * "user": "Jack",
     * rating: 5,
     * comment: "hello world",
     * gid: 5,
     * posted: new Date(),
     * name: "bgName1"
     * });
     */

    public void insertReview(String user, Integer rating, String comment, Integer gid, LocalDate date, String name) {

        JsonObject toInsert = Json.createObjectBuilder()
                .add("user", user)
                .add("rating", rating)
                .add("comment", comment)
                .add("gid", gid)
                .add("date", date.toString())
                .add("name", name)
                .build();

        Document review = Document.parse(toInsert.toString());
        Document inserted = mongoTemplate.insert(review, C_REVIEWS);
        System.out.println(inserted.toJson());
    }

    /*
     * db.reviews.find(
     * {_id: ObjectId("6396c4073fab32dd136e8916")}
     * );
     */

    public String getCommentById(String reviewId) {

        System.out.println("review ID >>>> " + reviewId);

        ObjectId docId = new ObjectId(reviewId);

        // String objectId = "ObjectId(\"%s\")".formatted(reviewId).trim();
        System.out.println(docId);

        // Criteria criteria = Criteria.where("_id").is(docId);

        // Integer gid = Integer.parseInt(reviewId);
        // Criteria criteria = Criteria.where("gid").is(gid);
        // Query query = Query.query(criteria);

        Document result = mongoTemplate.findById(docId, Document.class, C_REVIEWS);

        if (result == null) {
            System.out.printf("%s does not exists.\n", reviewId);
            return null;
        } else {
            String comment = result.getString("comment");
            System.out.println(comment);

            System.out.printf("%s exists and will be updated momentarily\n", comment);

            return comment;
        }

    }

    /*
     * db.reviews.updateOne(
     * {_id: ObjectId("6396c4073fab32dd136e8916")},
     * {
     * $set:{rating: 5, comments: "Hi there"}
     * }
     * );
     */

    public Long updateReviewById(String reviewId, Integer rating, String comment, LocalDate date) {

        ObjectId objectId = new ObjectId(reviewId);
        // Criteria criteria =
        // Criteria.where("_id").is("ObjectId(\"%s\")".formatted(reviewId));
        Criteria criteria = Criteria.where("_id").is(objectId);
        Query query = Query.query(criteria);

        Edit edit = new Edit();
        edit.setComment(comment);
        edit.setDate(date);
        edit.setRating(rating);

        System.out.println(edit.toString());

        Update updateOps = new Update()
                .set("rating", rating)
                .set("comment", comment)
                .set("date", LocalDate.now().toString())
                // fix push _class
                .push("edited").each(edit);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, updateOps, Document.class, C_REVIEWS);

        System.out.printf("Documents updated: %d\n", updateResult.getModifiedCount());

        return updateResult.getModifiedCount();
    }

    /*
     *  db.reviews.find(
     *      {_id: ObjectId("639703d63fab32dd136e8918")}
     *  );
     */

    public boolean findEditedById(String reviewId) {
        ObjectId docId = new ObjectId(reviewId);
        Document result = mongoTemplate.findById(docId, Document.class, C_REVIEWS);
        if (result.getList("edited", Document.class) == null) {
            return false;
        } else {
            return true;
        }
    }

    /*
     *  db.reviews.find(
     *      {_id: ObjectId("639703d63fab32dd136e8918")}
     *  );
     */

    public List<Edit> getEditedById(String reviewId) throws Exception {

        ObjectId docId = new ObjectId(reviewId);
        Document result = mongoTemplate.findById(docId, Document.class, C_REVIEWS);
        List<Edit> edited = new LinkedList<>();

        if (result.getList("edited", Document.class) == null) {
            System.out.println("There are no edits for this review ID.");
            return null;
        } else {
            edited = result.getList("edited", Document.class).stream().map(e -> Edit.createEdit(e)).toList();
            return edited;
        }
    }

    /*
     * db.reviews.find(
     * {_id: ObjectId("639703d63fab32dd136e8918")}
     * );
     */

    public Review getReviewById(String reviewId) {
        ObjectId docId = new ObjectId(reviewId);
        Document result = mongoTemplate.findById(docId, Document.class, C_REVIEWS);

        if (result == null) {
            System.out.printf("%s does not exists.\n", reviewId);
            return null;
        } else {
            Review review = new Review();
            String user = result.getString("user");
            Integer rating = result.getInteger("rating");
            String comment = result.getString("comment");
            Integer gid = result.getInteger("gid");

            LocalDate date = result.getDate("posted").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String name = result.getString("name");

            review.setUser(user);
            review.setRating(rating);
            review.setComment(comment);
            review.setGid(gid);
            review.setDate(date);
            review.setName(name);

            return review;
        }
    }

}
