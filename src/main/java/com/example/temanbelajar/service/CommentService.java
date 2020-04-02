package com.example.temanbelajar.service;

import com.example.temanbelajar.model.Blog;
import com.example.temanbelajar.model.Comment;
import com.example.temanbelajar.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CommentService
 */
@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    // public Comment save(Blog blog, Comment commentData) {
    //     // commentData.setGuest_email(commentData.getGuest_email());
    //     // commentData.setContent(commentData.getContent());
    //     commentData.setBlog(blog.getId());
        
    //     return commentRepository.save(commentData);
    // }

    public Comment update(Long id, Comment comment) {

        comment.setId(id);

        return commentRepository.save(comment);

    }

   

    
    
}