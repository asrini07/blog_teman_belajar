package com.example.temanbelajar.repository;

import com.example.temanbelajar.model.Author;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Authorrepository
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    
}