package vttp.day21.jdbc.repository;

public class Queries {
    public static final String SQL_SELECT_BOOKS_BY_RATING = "select book_id, title, description, rating, image_url from book2018 where rating >= ? order by title";
    
    public static final String SQL_SELECT_BOOKS_BY_TITLE = "select book_id, title, description, rating, image_url from book2018 where title like ? limit ?";
}
