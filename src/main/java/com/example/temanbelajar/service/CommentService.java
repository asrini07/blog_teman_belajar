package com.example.temanbelajar.service;

import com.example.temanbelajar.dto.request.CommentRequestDto;
import com.example.temanbelajar.dto.response.CommentResponseDto;
import com.example.temanbelajar.model.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * CommentService
 */
public interface CommentService {

    Page<CommentResponseDto> findAll(Long blogId, Pageable pageable);

    Page<CommentResponseDto> findByNameParams(Long blogId, Pageable pageable, String param);

    Comment save(Long blogId, CommentRequestDto commentData);
    
    CommentResponseDto findByIdAndBlogId(Long commentId, Long blogId);

    void deleteCommentId(Long commentId, Long blogId);
    
    Comment update(Long commentId, Long blogId, CommentRequestDto commentData);
    
    
}