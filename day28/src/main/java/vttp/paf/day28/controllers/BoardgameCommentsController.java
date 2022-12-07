package vttp.paf.day28.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.paf.day28.models.BoardgameComments;
import vttp.paf.day28.models.Game;
import vttp.paf.day28.repositories.BGGRepo;

@Controller
@RequestMapping(path="/search")
public class BoardgameCommentsController {
    
    @Autowired
    private BGGRepo bggRepo; 

    @GetMapping
    public String getBoardgameComments(@RequestParam("boardgame") String name, Model model) {
        
        System.out.println("REQUESTPARAM >>>>> " + name);

        Optional<Game> opt = bggRepo.search(name); 
        
        if(opt.isEmpty())
        return "not-found"; 
        
        Game game = opt.get(); 
        System.out.println(">>>> boardgameComments: " + game.toString());
        

        model.addAttribute("game", game);
        model.addAttribute("comments", game.getBgComments());
        model.addAttribute("name", name);
        return "comments";
    }

}
