package vttp.day21.jdbc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.day21.jdbc.model.Book;
import vttp.day21.jdbc.repository.BookRepository;

@Controller
@RequestMapping(path = "/search")
public class SearchController {
    
    @Autowired
    private BookRepository bookRepo; 

    // GET /search?bookName=jack&resultCount=10
    @GetMapping
    public String search(@RequestParam String bookName, @RequestParam Integer resultCount, Model model) {
        
        List<Book> books = bookRepo.getBooksByTitle(bookName, resultCount);
        
        // populate the model with the bindings
        model.addAttribute("bookName", bookName);
        model.addAttribute("resultCount", resultCount);
        model.addAttribute("books", books);
        model.addAttribute("hasResult", books.size()>0);
        return "search-results";
    }
}
