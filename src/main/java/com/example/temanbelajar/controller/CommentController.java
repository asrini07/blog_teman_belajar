package com.example.temanbelajar.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.temanbelajar.config.pagination.ConfigPage;
import com.example.temanbelajar.config.pagination.ConfigPageable;
import com.example.temanbelajar.config.pagination.PageConverter;
import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.dto.request.RequestCommentDto;
import com.example.temanbelajar.dto.response.ResponseCommentDto;
import com.example.temanbelajar.model.Comment;
import com.example.temanbelajar.repository.BlogRepository;
import com.example.temanbelajar.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * CommentController
 */
@RestController
@RequestMapping("/posts")
public class CommentController {

    @Autowired 
    CommentService commentService;

    @Autowired
    BlogRepository blogRepository;

    @GetMapping("/{blogId}/comments")
    public ResponseBaseDto<ConfigPage<ResponseCommentDto>> getComment(@PathVariable Long blogId, ConfigPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request){

        try {

            Page<ResponseCommentDto> comment;

            if (param != null) {
                comment = commentService.findByNameParams(blogId, ConfigPageable.convertToPageable(pageable), param);
            } else {
                comment = commentService.findAll(blogId, ConfigPageable.convertToPageable(pageable));
            }

            PageConverter<ResponseCommentDto> converter = new PageConverter<>();
            String url = String.format("%s://%s:%d/posts/"+blogId+"/comments",request.getScheme(),  request.getServerName(), request.getServerPort());

            String search = "";

            if(param != null){
                search += "&param="+param;
            }

            ConfigPage<ResponseCommentDto> respon = converter.convert(comment, url, search);

            return ResponseBaseDto.ok(respon);


        } catch (Exception e) {

            return ResponseBaseDto.error(200, e.getMessage());
        
        }
    }

    @PostMapping("/{blogId}/comments")
    public ResponseBaseDto createBlogComment(@PathVariable Long blogId, @RequestBody RequestCommentDto commentData) {

        try {

            Comment comment = commentService.save(blogId, commentData);

            return ResponseBaseDto.saved(comment);
            
        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }

    }

    @GetMapping("/{blogId}/comments/{id}")
    public ResponseBaseDto<ResponseCommentDto> getCommentById(@PathVariable Long blogId, @PathVariable Long id){

        try {

            return ResponseBaseDto.ok(commentService.findByIdAndBlogId(id, blogId));

        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }

    }

    @PutMapping("/{blogId}/comments/{commentId}")
    public ResponseBaseDto updateBlogComment(@PathVariable Long blogId, @PathVariable Long commentId, @RequestBody RequestCommentDto commentData) {

        try {

            Comment comment = commentService.update(commentId, blogId, commentData);

            return ResponseBaseDto.saved(comment);
            
        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }

    }

    @DeleteMapping("/{blogId}/comments")
    public ResponseBaseDto deleteCommentRequest(@PathVariable Long blogId, @RequestBody Comment commentData) {

        try {

            commentService.deleteCommentId(commentData.getId(), blogId);

            return ResponseBaseDto.ok();

        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }


    }

}