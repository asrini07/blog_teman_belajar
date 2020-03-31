package com.example.temanbelajar.controller;

import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.model.Comment;
import com.example.temanbelajar.repository.CommentRepository;
import com.example.temanbelajar.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CommentController
 */
@RestController
@RequestMapping("/blogs")
public class CommentController {

    @Autowired 
    CommentRepository commentRepository;

    @Autowired 
    CommentService commentService;

    @GetMapping("/{blogId}/comments")
    public ResponseEntity<ResponseBaseDto> getComment(@PathVariable Long blogId) {
        ResponseBaseDto response = new ResponseBaseDto<>();

        response.setData(commentRepository.findCommentBlog(blogId));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/{blog}/comments")
    public ResponseEntity<ResponseBaseDto> createBlogComment(@PathVariable Long blog, @RequestBody Comment comment) {
        
        ResponseBaseDto response = new ResponseBaseDto();

        try {

           // response.setData(commentRepository.save(comment));
            // Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));
            
            // comment.setContent(commentData.getContent());
            // comment.setGuestEmail(commentData.getGuestEmail());

            // response.setStatus(true);
            // response.setCode(200);
            // response.setMessage("success");
            response.setData(commentRepository.save(comment));

            return new ResponseEntity<>(response ,HttpStatus.OK);

        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage() );

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }

    }

    @GetMapping("/{blog}/comments/{id}")
    public ResponseEntity<ResponseBaseDto> getCommentById(@PathVariable Long blog, @PathVariable Long id){
        ResponseBaseDto response = new ResponseBaseDto<>();

        Comment comment = commentRepository.findCommentId(id, blog);

        response.setData(comment);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{blog}/comments/{id}")
    public ResponseEntity<ResponseBaseDto> deleteComment(@PathVariable Long blog, @PathVariable Long id) {
        ResponseBaseDto response = new ResponseBaseDto<>();

        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}