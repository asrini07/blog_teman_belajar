package com.example.temanbelajar.service;

import com.example.temanbelajar.model.Blog;
import com.example.temanbelajar.repository.BlogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BlogService
 */
@Service
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    public Blog update(Long id, Blog blog) {

        blog.setId(id);

        return blogRepository.save(blog);
    }
}