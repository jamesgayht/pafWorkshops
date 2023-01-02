package vttp.paf.pafMockAssessment.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.paf.pafMockAssessment.models.User;
import static vttp.paf.pafMockAssessment.repositories.Queries.*;

@Repository
public class UserRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate; 

    public User getUserByUserId(String userId) {
        // perform the query
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_USER_BY_ID, userId);

        User user = new User(); 

        if(rs.next()) 
            user = User.createUser(rs); 

        System.out.println("USER >>>>> " + user.toString());
        
        return user; 
    }

    public User getUserByUsername (String username) {

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_USER_BY_USERNAME, username); 

        User user = new User(); 

        if(rs.next()) 
            user = User.createUser(rs);

        return user; 
        
    }

    public Integer createUser (User user) {
        
        return jdbcTemplate.update(SQL_INSERT_USER, user.getUserId(), user.getUsername(), user.getName()); 
        
    }

}
