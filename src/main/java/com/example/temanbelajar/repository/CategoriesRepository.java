package com.example.temanbelajar.repository;

import com.example.temanbelajar.model.Categories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * CategoriesRepository
 */
public interface CategoriesRepository extends JpaRepository<Categories, Long>{

    @Query("select e from #{#entityName} e where e.name like %:param% ")
	Page<Categories> findByNameParams(Pageable pageable, String param);
    
}