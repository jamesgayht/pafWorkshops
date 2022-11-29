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
@RequestMapping(path = "/next")
public class NextController {

    @Autowired
    private BookRepository bookRepo; 
    
    @GetMapping
    public String searchNext(@RequestParam String bookName, @RequestParam Integer resultCount, @RequestParam Integer offset, Model model) {
        List<Book> books = bookRepo.getBooksByTitleNext(bookName, resultCount, offset);

        // populate the model with the bindings
        model.addAttribute("bookName", bookName);
        model.addAttribute("resultCount", resultCount);
        model.addAttribute("books", books);
        model.addAttribute("hasResult", books.size()>0); 
        return "next";
    }
}
