package com.example.temanbelajar.repository;

import com.example.temanbelajar.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
    @Query("select e from #{#entityName} e where e.roletitle like %:param% ")
    Role findByTitle(String param);

}