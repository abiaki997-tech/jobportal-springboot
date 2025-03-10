package com.jobsearch.indeedjob.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessAuthenticationHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        System.out.println("The username :"+username);

        Boolean hasJobSeeker = authentication.getAuthorities().stream().anyMatch(r->r.getAuthority().equals("Job Seeker"));
        Boolean hasRecuriter = authentication.getAuthorities().stream().anyMatch(r->r.getAuthority().equals("Recruiter"));

       if(hasRecuriter || hasRecuriter){
           response.sendRedirect("/dashboard");
       }
    }
}
