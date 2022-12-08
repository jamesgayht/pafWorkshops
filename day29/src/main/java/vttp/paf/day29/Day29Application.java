package vttp.paf.day29;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.json.JsonObject;
import vttp.paf.day29.models.MarvelCharacter;
import vttp.paf.day29.services.MarvelService;

@SpringBootApplication
// public class Day29Application implements CommandLineRunner{
public class Day29Application {

	@Autowired
	private MarvelService marvelService; 

	// @Override
	// public void run(String ... args) {
	// 	marvelService.search("doctor");

	// 	System.exit(0);
	// }

	public static void main(String[] args) {
		SpringApplication.run(Day29Application.class, args);
	}

}
