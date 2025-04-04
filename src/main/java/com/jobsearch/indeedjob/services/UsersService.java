package com.jobsearch.indeedjob.services;

import com.jobsearch.indeedjob.entity.JobSeekerProfile;
import com.jobsearch.indeedjob.entity.RecruiterProfile;
import com.jobsearch.indeedjob.entity.Users;
import com.jobsearch.indeedjob.repository.JobSeekerProfileRepository;
import com.jobsearch.indeedjob.repository.RecruiterProfileRepository;
import com.jobsearch.indeedjob.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private final UserRepository userRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final PasswordEncoder passwordEncoder;
    public UsersService(UserRepository userRepository,
                        JobSeekerProfileRepository jobSeekerProfileRepository,
                        RecruiterProfileRepository recruiterProfileRepository,
                        PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.jobSeekerProfileRepository=jobSeekerProfileRepository;
        this.recruiterProfileRepository=recruiterProfileRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public Users addNew(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser = userRepository.save(users);
        int userTypeId= users.getUserTypeId().getUserTypeId();

        if(userTypeId == 1){
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }else{
           jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }
        return savedUser;
    }

    public Optional<Users> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


    public Object getCurrentUserProfile() {

      Authentication authentication =SecurityContextHolder.getContext().getAuthentication();

      if(!(authentication instanceof AnonymousAuthenticationToken)){
         String username =authentication.getName();
         Users users = userRepository.findByEmail(username)
                     .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
         int userId= users.getUserId();
         if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
             RecruiterProfile recruiterProfile = recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
             return  recruiterProfile;
         }else{
             JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile());
             return jobSeekerProfile;
         }


      }
      return  null;
    }
}
