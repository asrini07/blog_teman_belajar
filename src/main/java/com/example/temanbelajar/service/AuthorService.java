package com.example.temanbelajar.service;

import com.example.temanbelajar.dto.request.AuthorRequestDto;
import com.example.temanbelajar.dto.request.AuthorRequestPassDto;
import com.example.temanbelajar.dto.request.AuthorRequestUpdateDto;
import com.example.temanbelajar.dto.response.AuthorResponseDto;
import com.example.temanbelajar.dto.response.AuthorResponseUpdateDto;
import com.example.temanbelajar.dto.response.PasswordResponseUpdateDto;
import com.example.temanbelajar.model.Author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * AuthorService
 */
public interface AuthorService {

    Page<AuthorResponseDto> findAll(Pageable pageable);

    AuthorResponseDto findById(Long authorId);

    Page<AuthorResponseDto> findByNameParams(Pageable pageable, String param);

    Author save(AuthorRequestDto request);

    AuthorResponseUpdateDto update(Long authorId, AuthorRequestUpdateDto authorData);

    PasswordResponseUpdateDto changePassword(Long authorId, AuthorRequestPassDto authorData);

    void deleteById(Long authorId);

    public Author findByUsername(String username);

    BCryptPasswordEncoder passwordEncoder();
    
}