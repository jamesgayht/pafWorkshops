package vttp.paf.day27ws.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.paf.day27ws.models.Edit;
import vttp.paf.day27ws.models.Review;
import vttp.paf.day27ws.repositories.ReviewsRepo;

@RestController
@RequestMapping(path = "/review", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewRESTController {

    @Autowired
    private ReviewsRepo reviewsRepo;

    @PostMapping
    public ResponseEntity<String> insertReview(@RequestBody MultiValueMap<String, String> form) throws Exception {

        String user = form.getFirst("user");
        Integer rating = Integer.parseInt(form.getFirst("rating"));
        if (rating > 10 || rating < 0) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Rating must be between 1 and 10");
        }

        String comment = "";
        if (form.getFirst("comment") == null || form.getFirst("comment").equals("")) {
            comment = "No comments";
        } else {
            comment = form.getFirst("comment");
        }

        Integer gid = Integer.parseInt(form.getFirst("gid"));

        System.out.printf("USER: %s, RATING: %d, COMMENT: %s, GID: %d\n", user, rating, comment, gid);

        LocalDate date = LocalDate.now();

        String name = "";
        System.out.println("GET GAME BY ID >>>>> " + reviewsRepo.getGameById(gid));

        if (reviewsRepo.getGameById(gid) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("This game id does not exist");
        } else {
            name = reviewsRepo.getGameById(gid);

            reviewsRepo.insertReview(user, rating, comment, gid, date, name);

            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Thank you for your review on %s!\n".formatted(name));
        }
    }

    @PutMapping(path = "/{reviewId}", params = "rating")
    // public ResponseEntity<String> updateReview(@PathVariable String reviewId,
    // @RequestBody MultiValueMap<String, String> form) throws Exception {
    public ResponseEntity<String> updateReview(@PathVariable String reviewId, @RequestParam("rating") Integer rating)
            throws Exception {

        String comment = reviewsRepo.getCommentById(reviewId);
        return updateReview(reviewId, rating, comment);

    }

    @PutMapping(path = "/{reviewId}", params = { "rating", "comment" })
    // public ResponseEntity<String> updateReview(@PathVariable String reviewId,
    // @RequestBody MultiValueMap<String, String> form) throws Exception {
    public ResponseEntity<String> updateReview(@PathVariable String reviewId, @RequestParam("rating") Integer rating,
            @RequestParam("comment") String comment) throws Exception {

        // Integer rating = Integer.parseInt(form.getFirst("rating"));

        // String comment = "";
        // if(form.getFirst("comment").equals("")) {
        // comment = "No comments";
        // }
        // else {
        // comment = form.getFirst("comment");
        // }

        System.out.printf("Rating>>> %d, Comment>>> %s\n", rating, comment);

        if (reviewsRepo.getCommentById(reviewId) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("This review id does not exist");
        } else {
            LocalDate date = LocalDate.now();
            reviewsRepo.updateReviewById(reviewId, rating, comment, date);

            return ResponseEntity.ok("Your review has been updated.");

        }
    }

    @GetMapping(path = "/{reviewId}")
    public ResponseEntity<String> getCommentAndRating(@PathVariable String reviewId) throws Exception {

        if (reviewsRepo.getReviewById(reviewId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("THis review ID does not exist.\n");
        } else {
            Review review = reviewsRepo.getReviewById(reviewId);
            if (reviewsRepo.findEditedById(reviewId) == false) {
                JsonObject results = Json.createObjectBuilder()
                        .add("user", review.getUser())
                        .add("rating", review.getRating())
                        .add("comment", review.getComment())
                        .add("gid", review.getGid())
                        .add("posted", review.getDate().toString())
                        .add("name", review.getName())
                        .add("edited", false)
                        .add("timestamp", LocalDateTime.now().toString())
                        .build();
                return ResponseEntity.ok(results.toString());
            } 

            else {

                JsonObject results = Json.createObjectBuilder()
                        .add("user", review.getUser())
                        .add("rating", review.getRating())
                        .add("comment", review.getComment())
                        .add("gid", review.getGid())
                        .add("posted", review.getDate().toString())
                        .add("name", review.getName())
                        .add("edited", true)
                        .add("timestamp", LocalDateTime.now().toString())
                        .build();

                return ResponseEntity.ok(results.toString());
            }
        }
    }

    @GetMapping(path = "/{reviewId}/history")
    public ResponseEntity<String> getFullEdits(@PathVariable String reviewId) throws Exception {

        if (reviewsRepo.getReviewById(reviewId) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("This review id does not exist.");
        } else {
            Review review = reviewsRepo.getReviewById(reviewId);
            List<Edit> edited = reviewsRepo.getEditedById(reviewId);
            review.setEdited(edited);
            JsonObject results = review.toJson();

            return ResponseEntity.ok(results.toString());
        }
    }
}
