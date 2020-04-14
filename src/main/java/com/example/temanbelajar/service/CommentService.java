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

    Page<ResponseCommentDto> findAll(Long blogId, Pageable pageable);

    Page<ResponseCommentDto> findByNameParams(Long blogId, Pageable pageable, String param);

    Comment save(Long blogId, RequestCommentDto commentData);
    
    ResponseCommentDto findByIdAndBlogId(Long commentId, Long blogId);

    void deleteCommentId(Long commentId, Long blogId);
    
    Comment update(Long commentId, Long blogId, RequestCommentDto commentData);
    
    
}