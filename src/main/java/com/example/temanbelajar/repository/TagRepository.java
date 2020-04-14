package com.example.temanbelajar.repository;

import com.example.temanbelajar.model.Tags;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * TagRepository
 */
public interface TagRepository extends JpaRepository<Tags, Long>{

    Tags findByName(String name);

    @Query("select e from #{#entityName} e where e.name like %:param% ")
    Page<Tags> search(Pageable pageable, String param);
    
}