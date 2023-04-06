package com.security.blogs.Security;

import com.security.blogs.Dao.UserRepo;
import com.security.blogs.Exceptions.EmailNotFoundException;
import com.security.blogs.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Finding User - " + username);

        //User user = this.userRepo.findByName(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        User user = this.userRepo.findByEmail(username).orElseThrow(() -> new EmailNotFoundException("User", "username", username));

        System.out.println("Found - " + user);

        return user;
    }
}
