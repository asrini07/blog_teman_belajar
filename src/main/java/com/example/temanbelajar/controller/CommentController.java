package com.example.temanbelajar.controller;

import java.util.List;

import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.dto.ResponseDto;
import com.example.temanbelajar.exeption.ResourceNotFoundException;
import com.example.temanbelajar.model.Blog;
import com.example.temanbelajar.model.Comment;
import com.example.temanbelajar.repository.BlogRepository;
import com.example.temanbelajar.repository.CommentRepository;
import com.example.temanbelajar.service.CommentService;
import com.example.temanbelajar.service.CommentServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CommentController
 */
@RestController
@RequestMapping("/posts")
public class CommentController {

    @Autowired 
    CommentRepository commentRepository;

    @Autowired 
    CommentService commentService;

    @Autowired
    BlogRepository blogRepository;

    @GetMapping("/{blogId}/comments")
    public ResponseEntity<ResponseBaseDto> getComment(@PathVariable Long blogId) {
        ResponseBaseDto response = new ResponseBaseDto<>();

        List<Comment> comment = commentRepository.findCommentBlog(blogId);

        response.setData(comment);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/{blogId}/comments")
    public ResponseEntity<ResponseBaseDto> createBlogComment(@PathVariable Long blogId, @RequestBody Comment commentData) {
        
        ResponseBaseDto response = new ResponseBaseDto();

        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));

        try {

            response.setData(blogRepository.findById(blogId).map(blogs -> {
                commentData.setBlog(blogs);
                return commentRepository.save(commentData);
            }).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId)));

            return new ResponseEntity<>(response ,HttpStatus.OK);

        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage() );

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }

    }

    @GetMapping("/{blogId}/comments/{id}")
    public ResponseEntity<ResponseBaseDto> getCommentById(@PathVariable Long blogId, @PathVariable Long id){
        ResponseBaseDto response = new ResponseBaseDto<>();

        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));

        Comment comment = commentRepository.findByIdAndBlogId(id, blogId);

        response.setData(comment);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{blogId}/comments/{id}")
    public ResponseEntity<ResponseBaseDto> deleteComment(@PathVariable Long blogId, @PathVariable Long id) {
        ResponseBaseDto response = new ResponseBaseDto<>();

        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));

        try {

            commentRepository.deleteCommentId(id, blogId);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{blogId}/comments/{commentId}")
    public ResponseEntity<ResponseBaseDto> updateBlogComment(@PathVariable Long blogId, @PathVariable Long commentId, @RequestBody Comment commentData) {

        ResponseBaseDto response = new ResponseBaseDto<>();

        try {
            response.setData(commentRepository.findById(commentId).map(comment -> {
                comment.setContent(commentData.getContent());
                return commentRepository.save(comment);
            }).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId)));

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{blogId}/comments")
    public ResponseEntity<ResponseBaseDto> deleteCommentRequest(@PathVariable Long blogId, @RequestBody Comment commentData) {
        ResponseBaseDto response = new ResponseBaseDto<>();

        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));

        try {

            commentRepository.deleteCommentId(commentData.getId(), blogId);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}