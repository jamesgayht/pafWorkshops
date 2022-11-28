package vttp.paf.day23.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Brewery {
    private String breweryName;

    public String getBreweryName() {
        return breweryName;
    }

    public void setBreweryName(String breweryName) {
        this.breweryName = breweryName;
    } 

    public static Brewery create(SqlRowSet rs) {
        final Brewery brewery = new Brewery();
        brewery.setBreweryName(rs.getString("breweries"));
        return brewery;
    }
}
