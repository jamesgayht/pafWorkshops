package vttp.paf.day23.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.paf.day23.models.BeerStyle;
import static vttp.paf.day23.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class StylesRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate; 

    public Integer createStyle(BeerStyle style) {
        return 0;
        
    }

    public List<BeerStyle> getBeerStyles() {
        List<BeerStyle> beerStyles = new LinkedList<>();
        BeerStyle style = new BeerStyle(); 
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_STYLES); 

        while(rs.next())
            beerStyles.add(style.create(rs));

        return beerStyles;
    }

    // get by style_name, but may not be as efficient as using style_id
    // public List<String> getStyles() {
    //     List<String> beerStyles = new LinkedList<>();
    //     final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_STYLES);

    //     while(rs.next())
    //         beerStyles.add(rs.getString("style_name"));

    //     return beerStyles;
    // }

    // gets by style_id which may be more efficient since id consumes less space 
    // public List<Integer> getStylesById() {
    //     List<Integer> beerStylesById = new LinkedList<>();
    //     final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_STYLES);

    //     while(rs.next())
    //         beerStylesById.add(rs.getInt("id"));

    //     return beerStylesById;
    // }
}
