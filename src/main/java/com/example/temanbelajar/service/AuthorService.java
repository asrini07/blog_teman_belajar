package com.example.temanbelajar.service;

import com.example.temanbelajar.model.Author;
import com.example.temanbelajar.repository.AuthorRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * AuthorService
 */
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
        //author.setPassword(author.getPassword());

        return authorRepository.save(author);
    }
    
}