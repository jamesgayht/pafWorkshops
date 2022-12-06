package vttp.paf.day27.repositories;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import vttp.paf.day27.models.Comment;

@Repository
public class CommentRepo {

    public static final String C_COMMENTS = "comments";

    @Autowired
    private MongoTemplate mongoTemplate;

    /*
     * db.comments.find(
     * {
     * $text:{ $search:"kids" }
     * },
     * {
     * score: {$meta: "textScore"}
     * }
     * );
     */

    public List<Comment> getCommentsByKeyword(String keywords) {
        return getCommentsByKeyword(keywords, 20, 0);
    }

    public List<Comment> getCommentsByKeyword(String keywords, Integer limit) {
        return getCommentsByKeyword(keywords, limit, 0); 
    }

    public List<Comment> getCommentsByKeyword(String keywords, Integer limit, Integer offset) {

        List<String> matches = new LinkedList<>();
        List<String> notMatch = new LinkedList<>();
        String[] keywordsArr = keywords.split(" ");

        for (int i = 0; i < keywordsArr.length; i++) {
            String word = keywordsArr[i].trim();
            if (word.startsWith("-")) {
                notMatch.add(word.substring(1));
            } else {
                matches.add(word);
            }
        }


        TextCriteria textCriteria = TextCriteria.forDefaultLanguage()
                .matchingAny(matches.toArray(new String[matches.size()]))
                .notMatchingAny(notMatch.toArray(new String[notMatch.size()]));

        TextQuery textQuery = (TextQuery)TextQuery.queryText(textCriteria)
                                .includeScore("score")
                                .sortByScore()
                                .limit(limit)
                                .skip(offset); 

        

        List<Document> results = mongoTemplate.find(textQuery, Document.class, C_COMMENTS);

        List<Comment> comments = new LinkedList<>();
        for(Document d:results)
            comments.add(Comment.createComment(d));

        return comments; 

        // using stream example 
        // return mongoTemplate.find(textQuery,Document.class, C_COMMENTS)
        //     .stream()
        //     .map(d -> Comment.createComment(d))
        //     .toList();
    }
}
