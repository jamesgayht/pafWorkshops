package vttp.paf.day22ws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.paf.day22ws.models.Rsvp;
import vttp.paf.day22ws.repositories.RsvpRepo;

@Service
public class RsvpService {
    
    @Autowired
    private RsvpRepo rsvpRepo; 

    public boolean createRsvp(final Rsvp rsvp) throws Exception {
        int count = rsvpRepo.createRsvp(rsvp);
        System.out.printf("Insert count: %d/n", count);
        return count > 0; 
    }
}
