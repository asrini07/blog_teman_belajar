package com.example.temanbelajar.service;

import com.example.temanbelajar.model.Author;
import com.example.temanbelajar.repository.AuthorRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * AuthorService
 */
@Slf4j
@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public Author save(Author author) {
        author.setPassword(passwordEncoder().encode(author.getPassword()));
        
        return authorRepository.save(author);
    }

    public Author update(Long id, Author author) {
        
        author.setId(id);

        return authorRepository.save(author);
    }

    public Author changePassword(Long id, Author author) {
        author.setId(id);
        author.setPassword(passwordEncoder().encode(author.getPassword()));

        return authorRepository.save(author);
    }

    public Page<Author> findAll(Pageable pageable) {
        try {

            return authorRepository.findAll(pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public Page<Author> findByNameParams(Pageable pageable, String param) {

        try {
            param = param.toLowerCase();
            return authorRepository.findByNameParams(pageable, param).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private Author fromEntity(Author author) {
        Author response = new Author();
        BeanUtils.copyProperties(author, response);
        return response;
    }
    
}