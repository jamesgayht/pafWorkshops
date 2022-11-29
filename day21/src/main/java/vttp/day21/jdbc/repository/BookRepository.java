package vttp.day21.jdbc.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.day21.jdbc.model.Book;
import static vttp.day21.jdbc.repository.Queries.*;

@Repository
public class BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate; 

    public List<Book> getBooksByRating(Float rating) {
        // perform the query
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_BOOKS_BY_RATING, rating);
        List<Book> books = new LinkedList<>();
        // move the cursor to the next row
        while(rs.next()) {
            books.add(Book.create(rs)); 
        }
        return books; 
    }

    public List<Book> getBooksByTitle(String bookName, Integer limit) {
        // perform the query
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_BOOKS_BY_TITLE, "%%%s%%".formatted(bookName), limit);

        final List<Book> results = new LinkedList<>();
        while(rs.next())
            results.add(Book.create(rs));

        return results;
    }

    public List<Book> getBooksByTitleNext(String bookName, Integer limit, Integer offset) {
        // perform the query 
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_BOOKS_BY_TITLE_NEXT, "%%%s%%".formatted(bookName), limit, offset); 

        final List<Book> results = new LinkedList<>(); 
        while(rs.next())
            results.add(Book.create(rs));

        return results;
    }
}
