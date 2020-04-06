package com.example.temanbelajar.service;

import com.example.temanbelajar.model.Blog;
import com.example.temanbelajar.repository.BlogRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * BlogService
 */
@Slf4j
@Service
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    public Blog update(Long id, Blog blog) {

        blog.setId(id);

        return blogRepository.save(blog);
    }

    public Page<Blog> findAll(Pageable pageable) {
        try {

            return blogRepository.findAll(pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public Page<Blog> findByNameParams(Pageable pageable, String param) {

        try {
            param = param.toLowerCase();
            return blogRepository.findByNameParams(pageable, param).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private Blog fromEntity(Blog blog) {
        Blog response = new Blog();
        BeanUtils.copyProperties(blog, response);
        return response;
    }

}