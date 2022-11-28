package vttp.paf.day22.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.paf.day22.models.User;
import vttp.paf.day22.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo; 

    public boolean createUser (final User user) throws Exception {
        int count = userRepo.createUser(user);
        System.out.printf("count: %d\n", count);
        return count > 0; 
    }

    public boolean authenticateUser (User user) {
        boolean result = userRepo.authenticate(user); 
        return result;
    }
}
