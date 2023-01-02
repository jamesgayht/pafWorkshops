package vttp.paf.pafMockAssessment.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.paf.pafMockAssessment.models.User;
import vttp.paf.pafMockAssessment.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUserByUserId(String userId) {

        User user = userRepository.getUserByUserId(userId); 
        
        if(user.getUserId() == null) {
            return Optional.empty();
        }
        else {
            return Optional.of(user); 
        }

    }

    public Optional<User> findUserByUsername(String username) {
        
        User user = userRepository.getUserByUsername(username); 
        
        if(user.getUsername() == null) {
            return Optional.empty();
        }
        else {
            return Optional.of(user);
        }

    }
    
    public String insertUser (final User user) throws Exception {
        int count = userRepository.createUser(user); 
        System.out.println("Insert count: " + count);
        return user.getUserId(); 
    }
    



}
    