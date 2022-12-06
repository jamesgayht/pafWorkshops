package vttp.paf.day27;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.result.DeleteResult;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@SpringBootApplication
// public class Day27Application implements CommandLineRunner {
public class Day27Application {

	@Autowired
	private MongoTemplate mongoTemplate;

	// @Override
	// public void run(String ... args) {

	// 	// insertNewDocument();
	// 	// insertFromJsonObject();
	// 	deleteJson();

	// 	System.exit(0);
	// }
	
	public void deleteJson() { 
		// Create a criteria 
		Criteria c = Criteria.where("title").regex("json", "i"); 
		Query query = Query.query(c); 

		// Delete the item
		DeleteResult deleteResult = mongoTemplate.remove(query, "posts"); 

		System.out.printf(">>>> DELETE COUNT: %d\n", deleteResult.getDeletedCount());
	}

	public void insertNewDocument() {
		// create a document to be inserted
		Document blog = new Document(); 
		blog.put("title", "Third blog"); 
		blog.put("date", new Date()); 
		blog.put("summary", "The meaning of life"); 

		// creating a single comment
		Document comment = new Document(); 
		comment.put("user", "jack");
		comment.put("comment", "I love your blog!"); 

		// create a list to hold the comments
		List<Document> comments = new LinkedList<>(); 
		comments.add(comment); 

		blog.put("comments", comments); 
		blog.put("tags", List.of("one", "two", "three")); 

		Document inserted = mongoTemplate.insert(blog, "posts"); 
		
		System.out.println(inserted.toJson());
	}

	public static void main(String[] args) {
		SpringApplication.run(Day27Application.class, args);
	}

	public void insertFromJsonObject() {
		// Json object in JsonObject or JsonArray
		JsonObject json = Json.createObjectBuilder()
							.add("title", "Concerning Json-P")
							.add("date", (new Date().toString()))
							.add("summary", "The meaning of life")
							.build(); 

		Document blog = Document.parse(json.toString());

		Document inserted = mongoTemplate.insert(blog, "posts");
		System.out.println("Inserted: " + inserted);
		
	}

}
