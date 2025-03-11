package com.jobsearch.indeedjob.services;

import com.jobsearch.indeedjob.entity.RecruiterProfile;
import com.jobsearch.indeedjob.repository.RecruiterProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;

    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository){
        this.recruiterProfileRepository=recruiterProfileRepository;
    }

    public Optional<RecruiterProfile> getById(Integer id){
        return recruiterProfileRepository.findById(id);
    }


    public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
        return recruiterProfileRepository.save(recruiterProfile);
    }
}
