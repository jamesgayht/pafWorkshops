package vttp.paf.day23.repositories;

public class Queries {
    
    public static final String SQL_SELECT_STYLES = 
    "select id, style_name from styles order by style_name";

    public static final String SQL_SELECT_BREWERIES_BY_STYLE = 
    "select * from breweries_by_style where style_id = ?"; 
}
