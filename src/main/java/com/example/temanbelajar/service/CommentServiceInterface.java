package com.example.temanbelajar.service;

import com.example.temanbelajar.dto.response.CommentReponseDto;

/**
 * CommentServiceInterface
 */
public interface CommentServiceInterface {

    CommentReponseDto findById(Long id);

}