package com.example.temanbelajar.service;

import javax.servlet.http.HttpServletRequest;

import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.dto.request.BlogRequestDto;
import com.example.temanbelajar.dto.request.BlogRequestUpdateDto;
import com.example.temanbelajar.dto.response.BlogResponseDto;
import com.example.temanbelajar.model.Blog;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * BlogService
 */
public interface BlogService {

    Page<BlogResponseDto> findAll(Pageable pageable);

    BlogResponseDto findById(Long blogId);

    Page<BlogResponseDto> findByNameParams(Pageable pageable, String param);

    Page<BlogResponseDto> findByAuthor(Pageable pageable, Long author_id);
    
    Page<BlogResponseDto> findByCategory(Pageable pageable, Long categories_id);

    Page<BlogResponseDto> findByTag(Pageable pageable, String tag_name);

    Blog save(BlogRequestDto request);

    Blog update(Long blogId, BlogRequestUpdateDto blogData);

    void deleteById(Long blogId);

    String updateFileBlog(MultipartFile file, Long blogId);

    //ResponseEntity<Resource> downloadFileBlog(String fileName, HttpServletRequest request);

}