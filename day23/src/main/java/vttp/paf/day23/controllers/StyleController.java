package vttp.paf.day23.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.paf.day23.models.BeerStyle;
import vttp.paf.day23.repositories.StylesRepo;

@Controller
@RequestMapping(path = "/")
public class StyleController {
    
    @Autowired
    private StylesRepo stylesRepo;

    // @GetMapping
    // public String getStyle(Model model) {
    //     List<String> stylesList = stylesRepo.getStyles();
    //     System.out.println(stylesList.toString());
    //     model.addAttribute("stylesList", stylesList);
    //     return "index"; 
    // }

    @GetMapping
    public String getStyleById(Model model) {
        
        List<BeerStyle> beerStyles = stylesRepo.getBeerStyles();
        System.out.println(" >>>> STYLE CONTROLLER <<<< " + beerStyles.toString());
        model.addAttribute("beerStyles", beerStyles);
        return "index";
    }

}
