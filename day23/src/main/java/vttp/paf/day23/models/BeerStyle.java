package vttp.paf.day23.models;


import org.springframework.jdbc.support.rowset.SqlRowSet;

public class BeerStyle {

    private String styleName;
    private Integer styleId;

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public BeerStyle create(SqlRowSet rs) {
        final BeerStyle beerStyle = new BeerStyle();
        beerStyle.setStyleId(rs.getInt("id"));
        beerStyle.setStyleName(rs.getString("style_name"));
        return beerStyle;
    }
}
