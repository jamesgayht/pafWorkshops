package vttp.paf.day27.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.paf.day27.models.Comment;
import vttp.paf.day27.repositories.CommentRepo;

@Controller
@RequestMapping(path = "/comments")
public class CommentController {

    @Autowired
    private CommentRepo commentRepo; 
    
    @PostMapping
    // public String getCommentsByKeyword(@RequestParam("keyword") String keywords, @RequestParam("score") Double score, Model model) {
    public String getCommentsByKeyword(@RequestBody MultiValueMap<String, String> form, Model model) {

        String keywords = form.getFirst("keywords");
        Double score = Double.parseDouble(form.getFirst("score")); 
        System.out.printf("Keywords: %s\nRating: %f\n\n", keywords, score);

        List<Comment> comments = commentRepo.getCommentsByKeyword(keywords); 
        System.out.println("Comments >>>> " + comments.toString());

        model.addAttribute("keywords", keywords); 
        model.addAttribute("score", score);
        model.addAttribute("comments", comments);

        // List<Comment> results = new LinkedList<>();
        // results.add(new Comment());


        return "comments"; 
    }
}
