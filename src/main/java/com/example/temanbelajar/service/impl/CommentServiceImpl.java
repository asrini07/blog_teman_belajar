package com.example.temanbelajar.service.impl;

import com.example.temanbelajar.config.DateTime;
import com.example.temanbelajar.dto.request.CommentRequestDto;
import com.example.temanbelajar.dto.response.CommentResponseDto;
import com.example.temanbelajar.exeption.ResourceNotFoundException;
import com.example.temanbelajar.model.Blog;
import com.example.temanbelajar.model.Comment;
import com.example.temanbelajar.repository.BlogRepository;
import com.example.temanbelajar.repository.CommentRepository;
import com.example.temanbelajar.service.CommentService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Comment";
    private static final String FIELD = "id";

    @Override
    public Page<CommentResponseDto> findAll(Long blogId, Pageable pageable) {

        try {

            Blog blog = blogRepository.findById(blogId).orElseThrow(()->new ResourceNotFoundException("Blog", "id", blogId));

            return commentRepository.findCommentBlogPagination(blogId, pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }

    }

    @Override
    public CommentResponseDto findByIdAndBlogId(Long commentId, Long blogId) {

        try {

            Blog blog = blogRepository.findById(blogId).orElseThrow(()->new ResourceNotFoundException("Blog", "id", blogId.toString()));

            Comment comment = commentRepository.findByIdAndBlogId(commentId, blogId);
            
            return fromEntity(comment);

        } catch (ResourceNotFoundException e) {
  
            log.error(e.getMessage(), e);
            throw e;

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Page<CommentResponseDto> findByNameParams(Long blogId, Pageable pageable, String param) {

        try {

            param = param.toLowerCase();
            return commentRepository.findByNameParamsBlog(blogId, pageable, param).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public void deleteCommentId(Long commentId, Long blogId) {

        try {

            Blog blog = blogRepository.findById(blogId).orElseThrow(()->new ResourceNotFoundException("Blog", "id", blogId.toString()));

            commentRepository.deleteCommentId(commentId, blogId);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Comment save(Long blogId, CommentRequestDto request) {

        try {
            
            Blog blog = blogRepository.findById(blogId).orElseThrow(()-> new ResourceNotFoundException("Blog", "id", blogId));

            Comment comment = new Comment();

            comment.setGuest_email(request.getGuest_email());
            comment.setContent(request.getContent());
            comment.setBlog(blog);
            
            BeanUtils.copyProperties(request, comment);
         
            return commentRepository.save(comment);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Comment update(Long commentId, Long blogId,  CommentRequestDto request) {

        try {
            Blog blog = blogRepository.findById(blogId).orElseThrow(()->new ResourceNotFoundException("Blog", "id", blogId.toString()));

            Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException(RESOURCE, FIELD, commentId.toString()));
            
            BeanUtils.copyProperties(request, comment);
            comment.setUpdated_at(dateTime.getCurrentDate());
            commentRepository.save(comment);
            return comment;

        } catch (ResourceNotFoundException e) {

            log.error(e.getMessage(), e);
            throw e;

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }


    private CommentResponseDto fromEntity(Comment comment) {

        CommentResponseDto response = new CommentResponseDto();
        BeanUtils.copyProperties(comment, response);
        return response;
        
    }


}