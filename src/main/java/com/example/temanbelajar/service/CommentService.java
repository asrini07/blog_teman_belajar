package com.example.temanbelajar.service;

import com.example.temanbelajar.model.Comment;
import com.example.temanbelajar.repository.CommentRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * CommentService
 */
@Slf4j
@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public Comment update(Long id, Comment comment) {

        comment.setId(id);

        return commentRepository.save(comment);

    }

    public Page<Comment> findAll(Long blogId, Pageable pageable) {
        try {
            return commentRepository.findCommentBlogPagination(blogId, pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    // public Page<Comment> findAll(Pageable pageable) {
    //     try {

    //         return commentRepository.findAll(pageable).map(this::fromEntity);

    //     } catch (Exception e) {

    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }

    public Page<Comment> findByNameParamsBlog(Long blogId, Pageable pageable,  String param) {

        try {
            param = param.toLowerCase();
            return commentRepository.findByNameParamsBlog(blogId, pageable, param).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private Comment fromEntity(Comment comment) {
        Comment response = new Comment();
        BeanUtils.copyProperties(comment, response);
        return response;
    }


   

    
    
}