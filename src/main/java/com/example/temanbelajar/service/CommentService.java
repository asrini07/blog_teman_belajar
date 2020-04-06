package com.example.temanbelajar.service;

import com.example.temanbelajar.model.Blog;
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

    public Page<Comment> findAll(Pageable pageable, Long blogId) {
        try {
            return commentRepository.findCommentBlog(pageable, blogId).map(this::fromEntity);
            //return ((Page<Comment>) commentRepository.findCommentBlog(pageable, blogId)).map(this::fromEntity);

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

    public Page<Comment> findByNameParamsBlog(Pageable pageable, String param, Long blogId) {

        try {
            param = param.toLowerCase();
            return commentRepository.findByNameParamsBlog(pageable, param, blogId).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public Page<Comment> findByNameParamsComment(Pageable pageable, String param, Long id, Long blogId) {

        try {
            param = param.toLowerCase();
            return commentRepository.findByNameParamsComment(pageable, param, id, blogId).map(this::fromEntity);

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