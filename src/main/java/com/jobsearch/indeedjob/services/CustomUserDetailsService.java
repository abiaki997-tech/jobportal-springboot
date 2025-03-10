package com.jobsearch.indeedjob.services;

import com.jobsearch.indeedjob.entity.Users;
import com.jobsearch.indeedjob.repository.UserRepository;
import com.jobsearch.indeedjob.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        return new CustomUserDetails(user);
    }
}
