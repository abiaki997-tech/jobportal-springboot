package com.jobsearch.indeedjob.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skills {

//    id, experience_level, name, years_of_experience, job_seeker_profile
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    private String experienceLevel;
    private String name;
    private String yearsOfExperience;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_seeker_profile")
    private JobSeekerProfile jobSeekerProfile;

}
