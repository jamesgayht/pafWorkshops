package vttp.paf.day22ws.repositories;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.paf.day22ws.models.Rsvp;
import static vttp.paf.day22ws.repositories.Queries.*; 

@Repository
public class RsvpRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate; 

    public List<Rsvp> getRsvps() {
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_ALL_RSVPS); 
        List<Rsvp> rsvps = new LinkedList<>();

        while(rs.next())
            rsvps.add(Rsvp.createRsvp(rs));

        return rsvps;
    }

    public List<Rsvp> getRsvpByName(String name) {
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_RSVP_BY_NAME, "%%%s%%".formatted(name));

        List<Rsvp> rsvps = new LinkedList<>(); 

        while(rs.next())
            rsvps.add(Rsvp.createRsvp(rs));

        return rsvps;
    }

    public Integer createRsvp(Rsvp rsvp) throws Exception {
        return jdbcTemplate.update(SQL_INSERT_RSVP, rsvp.getName(), rsvp.getEmail(), rsvp.getPhone(), rsvp.getConfirmationDate(), rsvp.getComments()); 
    }
}
