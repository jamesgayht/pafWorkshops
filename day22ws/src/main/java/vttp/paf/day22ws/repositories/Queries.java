package vttp.paf.day22ws.repositories;

public class Queries {
    
    public static final String SQL_SELECT_ALL_RSVPS = "select * from rsvp order by id"; 

    public static final String SQL_SELECT_RSVP_BY_NAME = "select * from rsvp where name like ?"; 

    public static final String SQL_SELECT_RSVP_BY_EMAIL = "select * from rsvp where email = ?";

    public static final String SQL_INSERT_RSVP = "insert into rsvp (name, email, phone, confirmation_date, comments) values (?, ?, ?, ?, ?)";
    
    public static final String SQL_UPDATE_RSVP_BY_EMAIL = "update rsvp set email = ? where email = ?"; 

    public static final String SQL_GET_RSVP_COUNT = "select count(distinct name) as count from rsvp";
}
