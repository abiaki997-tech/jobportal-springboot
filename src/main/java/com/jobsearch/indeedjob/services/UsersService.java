package com.jobsearch.indeedjob.services;

import com.jobsearch.indeedjob.entity.JobSeekerProfile;
import com.jobsearch.indeedjob.entity.RecruiterProfile;
import com.jobsearch.indeedjob.entity.Users;
import com.jobsearch.indeedjob.repository.JobSeekerProfileRepository;
import com.jobsearch.indeedjob.repository.RecruiterProfileRepository;
import com.jobsearch.indeedjob.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private final UserRepository userRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    public UsersService(UserRepository userRepository,JobSeekerProfileRepository jobSeekerProfileRepository,RecruiterProfileRepository recruiterProfileRepository){
        this.userRepository=userRepository;
        this.jobSeekerProfileRepository=jobSeekerProfileRepository;
        this.recruiterProfileRepository=recruiterProfileRepository;
    }

    public Users addNew(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
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


}
