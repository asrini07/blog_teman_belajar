package com.example.temanbelajar.service;

import com.example.temanbelajar.dto.request.RequestBlogDto;
import com.example.temanbelajar.dto.request.RequestUpdateBlogDto;
import com.example.temanbelajar.dto.response.ResponseBlogDto;
import com.example.temanbelajar.model.Blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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


}