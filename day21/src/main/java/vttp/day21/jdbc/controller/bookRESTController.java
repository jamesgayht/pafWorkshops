package vttp.day21.jdbc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import vttp.day21.jdbc.model.Book;
import vttp.day21.jdbc.repository.BookRepository;

@RestController
@RequestMapping(path = "/api/rating", produces = MediaType.APPLICATION_JSON_VALUE)
public class bookRESTController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping(path = "{rating}")
    public ResponseEntity<String> getRating(@PathVariable Float rating) {
        // query the database for the books
        List<Book> books = bookRepository.getBooksByRating(rating);

        // build the result
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(Book b : books) {
            // create a method in book.java to convert them into json
            arrayBuilder.add(b.toJSON());
        }
        JsonArray result = arrayBuilder.build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }
    
}
