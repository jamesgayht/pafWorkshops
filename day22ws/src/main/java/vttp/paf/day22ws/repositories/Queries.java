package vttp.paf.day22ws.repositories;

public class Queries {
    
    public static final String SQL_SELECT_ALL_RSVPS = "select * from rsvp order by id"; 

    public static final String SQL_SELECT_RSVP_BY_NAME = "select * from rsvp where name like ?"; 

    public static final String SQL_INSERT_RSVP = "insert into rsvp (name, email, phone, confirmation_date, comments) values (?, ?, ?, ?, ?)"; 
}
