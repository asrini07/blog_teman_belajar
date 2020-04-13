package com.example.temanbelajar.service;

import com.example.temanbelajar.dto.request.RequestCommentDto;
import com.example.temanbelajar.dto.response.ResponseCommentDto;
import com.example.temanbelajar.model.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * CommentService
 */
public interface CommentService {

    Page<ResponseCommentDto> findAll(Pageable pageable);

    ResponseCommentDto findById(Long categoryId);

    Page<ResponseCommentDto> findByNameParams(Pageable pageable, String param);

    Comment save(RequestCommentDto request);

    Comment update(Long categoryId, RequestCommentDto categoryData);

    void deleteById(Long categoryId);  
    
}