package vttp.paf.day23.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.paf.day23.models.Brewery;
import vttp.paf.day23.services.BreweryService;

@Controller
@RequestMapping(path = "/search")
public class BreweryController {

    @Autowired
    private BreweryService breweryService;
    
    @GetMapping
    public String showBreweries(@RequestParam("style") Integer beerStyle, Model model) {
        List<Brewery> breweries = new LinkedList<>();
        breweries = breweryService.getBreweriesById(beerStyle);
        System.out.println("<<<< BREWERY CONTROLLER >>>>" + breweries.toString());
        model.addAttribute("breweries", breweries);

        return "breweries";
    }
}
