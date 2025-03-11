package com.jobsearch.indeedjob.controller;

import com.jobsearch.indeedjob.entity.RecruiterProfile;
import com.jobsearch.indeedjob.entity.Users;
import com.jobsearch.indeedjob.repository.UserRepository;
import com.jobsearch.indeedjob.services.RecruiterProfileService;
import com.jobsearch.indeedjob.utils.FileUploadUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UserRepository userRepository;
    private final RecruiterProfileService recruiterProfileService;

    public RecruiterProfileController(UserRepository userRepository,RecruiterProfileService recruiterProfileService){
        this.userRepository=userRepository;
        this.recruiterProfileService=recruiterProfileService;
    }
    @GetMapping("/")
    public String recruiterProfile(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            Users users =userRepository.findByEmail(currentUsername).
                    orElseThrow(()-> new UsernameNotFoundException("User Not Found"));

            Optional<RecruiterProfile> recruiterProfile =recruiterProfileService.getById(users.getUserId());

            recruiterProfile.ifPresent(profile -> model.addAttribute("profile", profile));
        }
        return  "recruiter_profile";
    }

    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile,
                         @RequestParam("image")MultipartFile multipartFile,Model model){

        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            Users users = userRepository.findByEmail(currentUsername)
                     .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));

            recruiterProfile.setUserId(users);
            recruiterProfile.setUserAccountId(users.getUserId());
        }
        model.addAttribute("profile",recruiterProfile);
        String fileName = "";
        if(!multipartFile.getOriginalFilename().isEmpty()){
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(fileName);
        }
        RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);

        String uploadDir = "photos/recruiter/"+savedUser.getUserAccountId();
        try {
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return "redirect:/dashboard";
    }


}
