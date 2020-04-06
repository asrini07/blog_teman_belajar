package com.example.temanbelajar.repository;

import com.example.temanbelajar.model.Author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Authorrepository
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByUsername(String username);

    @Query("select e from #{#entityName} e where e.first_name like %:param% OR "
    + "e.last_name like %:param% OR e.username like %:param%")
	Page<Author> findByNameParams(Pageable pageable, String param);

}