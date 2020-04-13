package com.example.temanbelajar.service;

import com.example.temanbelajar.dto.request.RequestAuthorDto;
import com.example.temanbelajar.dto.request.RequestAuthorPassDto;
import com.example.temanbelajar.dto.request.RequestUpdateAuthorDto;
import com.example.temanbelajar.dto.response.ResponseAuthorDto;
import com.example.temanbelajar.model.Author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * AuthorService
 */
public interface AuthorService {

    Page<ResponseAuthorDto> findAll(Pageable pageable);

    ResponseAuthorDto findById(Long authorId);

    Page<ResponseAuthorDto> findByNameParams(Pageable pageable, String param);

    Author save(RequestAuthorDto request);

    Author update(Long authorId, RequestUpdateAuthorDto authorData);

    Author changePassword(Long authorId, RequestAuthorPassDto authorData);

    void deleteById(Long authorId);
    
}