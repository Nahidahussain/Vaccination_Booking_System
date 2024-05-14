package com.vaccinationbookingsystem.Service.jwt;



import com.vaccinationbookingsystem.model.User;
import com.vaccinationbookingsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;




@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUserName method");

        // Find user by email (username) in the database
        Optional<User> optionalUser = userRepository.findFirstByEmailId(username);

        // Throw exception if user not found
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Create UserDetails object with user details
        return new org.springframework.security.core.userdetails.User(
                optionalUser.get().getEmailId(),  // username
                optionalUser.get().getPassword(),  // password
                new ArrayList<>()  // empty list of authorities
        );
    }
}
