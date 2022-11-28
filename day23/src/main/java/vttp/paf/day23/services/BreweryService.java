package vttp.paf.day23.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.paf.day23.models.Brewery;
import vttp.paf.day23.repositories.BreweryRepo;
import vttp.paf.day23.repositories.StylesRepo;

@Service
public class BreweryService {
    
    @Autowired
    private StylesRepo stylesRepo;

    @Autowired
    private BreweryRepo breweryRepo; 

    // public List<BeerStyle> getStylesRepo() {
    //     return stylesRepo.getBeerStyles();
    // }

    public List<Brewery> getBreweriesById(Integer beerStyleId) {
        return breweryRepo.getBreweriesByStyle(beerStyleId);
    }
}
