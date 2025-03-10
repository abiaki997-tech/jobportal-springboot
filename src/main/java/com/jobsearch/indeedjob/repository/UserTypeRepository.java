package com.jobsearch.indeedjob.repository;

import com.jobsearch.indeedjob.entity.UsersType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UsersType,Integer> {
}
