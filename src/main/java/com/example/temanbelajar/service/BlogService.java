package com.example.temanbelajar.service;

import javax.servlet.http.HttpServletRequest;

import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.dto.request.RequestBlogDto;
import com.example.temanbelajar.dto.request.RequestUpdateBlogDto;
import com.example.temanbelajar.dto.response.ResponseBlogDto;
import com.example.temanbelajar.dto.response.ResponseUploadDto;
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

    Page<ResponseBlogDto> findAll(Pageable pageable);

    ResponseBlogDto findById(Long blogId);

    Page<ResponseBlogDto> findByNameParams(Pageable pageable, String param);

    Page<ResponseBlogDto> findByAuthor(Pageable pageable, Long author_id);
    
    Page<ResponseBlogDto> findByCategory(Pageable pageable, Long categories_id);

    Page<ResponseBlogDto> findByTag(Pageable pageable, String tag_name);

    Blog save(RequestBlogDto request);

    Blog update(Long blogId, RequestUpdateBlogDto blogData);

    void deleteById(Long blogId);

    String updateFileBlog(MultipartFile file, Long blogId);

    //ResponseEntity<Resource> downloadFileBlog(String fileName, HttpServletRequest request);

}