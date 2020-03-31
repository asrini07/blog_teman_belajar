package com.example.temanbelajar.service;

import com.example.temanbelajar.dto.response.ReponseCommentDto;

/**
 * CommentServiceInterface
 */
public interface CommentServiceInterface {

    ReponseCommentDto findById(Long id);

}