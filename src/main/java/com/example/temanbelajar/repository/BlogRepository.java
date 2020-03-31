package com.example.temanbelajar.repository;

import com.example.temanbelajar.model.Blog;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BlogRepository
 */
public interface BlogRepository extends JpaRepository<Blog, Long>{

    
}