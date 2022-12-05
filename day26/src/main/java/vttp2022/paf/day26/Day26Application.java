package vttp2022.paf.day26;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp2022.paf.day26.repositories.TVShowRepository;

@SpringBootApplication
public class Day26Application {
// implements CommandLineRunner {

	@Autowired
	private TVShowRepository tvShowRepo; 

	// @Override
	// public void run(String... args) {
		
	// 	List<Document> results = tvShowRepo.findTVShowByLanguage("English"); 
	// 	List<Document> ratingResults = tvShowRepo.findTVShowsByRating(6.5f); 

	// 	for(Document d:ratingResults) {

	// 		// findTVShowByRating 
	// 		Document ratingDoc = d.get("rating", Document.class); 

	// 		System.out.printf("Name: %s\nRating: %s\nLanguage: %s\n\n", d.getString("name"), ratingDoc.get("average"), d.getString("language"));

	// 		// >>>>> findTVShowByLanguage Method A <<<<<<
	// 		// Document ratingDoc = d.get("rating", Document.class); 
	// 		// System.out.printf("Name: %s\nSummary: %s\nRating: %s\n", d.getString("name"), d.getString("summary"), ratingDoc.get("average"));

	// 		// >>>>>> findTVShowByLanguage Method B <<<<< 
	// 		// this doesnt work cause not all of rating.average is a double, some art ints
	// 		// System.out.printf("Name: %s\nSummary: %s\nRating: %s\n", d.getString("name"), d.getString("summary"), d.getDouble("rating.average"));

	// 		// to get the json Document
	// 		// System.out.println(d.toJson());
	// 	}


	// 	System.exit(0);
	// }

	public static void main(String[] args) {
		SpringApplication.run(Day26Application.class, args);
	}

}
