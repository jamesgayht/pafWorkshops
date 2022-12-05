package vttp2022.paf.day26.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import vttp2022.paf.day26.models.TVShow;
import vttp2022.paf.day26.repositories.TVShowRepository;

@RestController
@RequestMapping (path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class TVShowRESTController {
    
    @Autowired
    private TVShowRepository tvShowRepo;

    @GetMapping(path = "/genres")
    public ResponseEntity<String> getGenres() {
        // query the database for the genres 
        List<String> genres = tvShowRepo.findTVShowGenres();

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for(String g: genres)
            arrayBuilder.add(g);

        JsonArray results = arrayBuilder.build(); 

        return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)                
        .body(results.toString());
    
    }

    @GetMapping(path = "/tvshow/{genre}")
    public ResponseEntity<String> getTVShowsByGenre(@PathVariable("genre") String genre) {

        List<TVShow> shows = tvShowRepo.findTvShowsByGenre(genre);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();   

        for(TVShow ts: shows)
            arrayBuilder.add(ts.toJSON()); 

        JsonArray results = arrayBuilder.build();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(results.toString()); 
    }

    @GetMapping(path = "/tvshow")
    public ResponseEntity<String> getTVShowsByName(@RequestParam("name") String name) {
        List<TVShow> shows = tvShowRepo.findTvShowsByName(name); 

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 

        for(TVShow ts:shows)
            arrayBuilder.add(ts.toJSON()); 
        JsonArray results = arrayBuilder.build();

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(results.toString());
    }

}
