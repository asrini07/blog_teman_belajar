package com.example.temanbelajar.repository;

import com.example.temanbelajar.model.Categories;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CategoriesRepository
 */
public interface CategoriesRepository extends JpaRepository<Categories, Long>{

    
}