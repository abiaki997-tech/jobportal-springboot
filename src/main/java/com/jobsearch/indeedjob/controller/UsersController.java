package com.jobsearch.indeedjob.controller;

import com.jobsearch.indeedjob.entity.Users;
import com.jobsearch.indeedjob.entity.UsersType;
import com.jobsearch.indeedjob.services.UserTypeService;
import com.jobsearch.indeedjob.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {


    private final UserTypeService userTypeService;
    private final UsersService usersService;
    @Autowired
    public UsersController(UserTypeService userTypeService,UsersService usersService){
        this.userTypeService=userTypeService;
        this.usersService=usersService;
    }

    //Get list of UserTypes
    @GetMapping("/register")
    public String register(Model model){
         List<UsersType> usersTypes = userTypeService.getAll();
         model.addAttribute("getAllTypes",usersTypes);
         model.addAttribute("user",new Users());
         return "register";
    }

    //save user in db
    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users,Model model){
        Optional<Users> optionalUsers =usersService.getUserByEmail(users.getEmail());

        if(optionalUsers.isPresent()){
            model.addAttribute("error","Email Already Exist !!!");
            List<UsersType> usersTypes = userTypeService.getAll();
            model.addAttribute("getAllTypes",usersTypes);
            model.addAttribute("user",new Users());
            return "register";
        }
        usersService.addNew(users);
        return "dashboard";
    }


}
