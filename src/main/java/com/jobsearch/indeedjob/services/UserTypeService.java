package com.jobsearch.indeedjob.services;

import com.jobsearch.indeedjob.entity.UsersType;
import com.jobsearch.indeedjob.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {

    private final  UserTypeRepository userTypeRepo;

    @Autowired
    public UserTypeService(UserTypeRepository userTypeRepo){
        this.userTypeRepo=userTypeRepo;
    }

    public List<UsersType> getAll(){
        return userTypeRepo.findAll();
    }


}
