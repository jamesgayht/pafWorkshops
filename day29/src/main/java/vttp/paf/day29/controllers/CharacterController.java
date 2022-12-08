package vttp.paf.day29.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.paf.day29.models.MarvelCharacter;
import vttp.paf.day29.repositories.MarvelCache;
import vttp.paf.day29.services.MarvelService;

@Controller
@RequestMapping(path = "/search")
public class CharacterController {

    @Autowired
    private MarvelService marvelService; 

    @Autowired
    private MarvelCache marvelCache; 
    
    @GetMapping
    public String searchCharacter(@RequestParam("character") String character, Model model) {
        System.out.println(">>>> REQUEST PARAM : " + character);

        List<MarvelCharacter> characters = null; 
        // System.out.println(">>>>> characters: " + characters.toString());
        Optional<List<MarvelCharacter>> opt = marvelCache.get(character);

        if(opt.isEmpty()) {
            characters = marvelService.search(character); 
            marvelCache.cache(character, characters);
            System.out.println(">>>> RESULTS NOT IN CACHE, CACHING NOW");
        } else {
            characters = opt.get();
            System.out.println(">>>>> RESULTS FROM CACHE");
        }

        model.addAttribute("character", character); 
        model.addAttribute("characters", characters); 

        return "search"; 
    }
}
