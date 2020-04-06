package com.example.temanbelajar.repository;

import com.example.temanbelajar.model.Blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * BlogRepository
 */
public interface BlogRepository extends JpaRepository<Blog, Long>{

    @Query("select e from #{#entityName} e where e.title like %:param% OR "
    + "e.content like %:param%")
    Page<Blog> findByNameParams(Pageable pageable, String param);
    
}