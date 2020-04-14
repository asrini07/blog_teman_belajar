package com.example.temanbelajar.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.temanbelajar.config.DateTime;
import com.example.temanbelajar.dto.request.RequestBlogDto;
import com.example.temanbelajar.dto.request.RequestUpdateBlogDto;
import com.example.temanbelajar.dto.response.ResponseBlogDto;
import com.example.temanbelajar.exeption.ResourceNotFoundException;
import com.example.temanbelajar.model.Author;
import com.example.temanbelajar.model.Blog;
import com.example.temanbelajar.model.Categories;
import com.example.temanbelajar.model.Tags;
import com.example.temanbelajar.repository.AuthorRepository;
import com.example.temanbelajar.repository.BlogRepository;
import com.example.temanbelajar.repository.CategoriesRepository;
import com.example.temanbelajar.repository.TagRepository;
import com.example.temanbelajar.service.BlogService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    CategoriesRepository categoriesRepository;
    
    @Autowired
    TagRepository tagRepository;

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Blog";
    private static final String FIELD = "id";

    @Override
    public Page<ResponseBlogDto> findAll(Pageable pageable) {

        try {

            return blogRepository.findAll(pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }

    }

    @Override
    public ResponseBlogDto findById(Long id) {

        try {

            Blog blog = blogRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));
            
            return fromEntity(blog);

        } catch (ResourceNotFoundException e) {

            log.error(e.getMessage(), e);
            throw e;

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Page<ResponseBlogDto> findByNameParams(Pageable pageable, String param) {

        try {

            param = param.toLowerCase();
            return blogRepository.findByNameParams(pageable, param).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public void deleteById(Long id) {

        try {

            Blog blog = blogRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));

            blogRepository.deleteById(id);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Blog save(RequestBlogDto blogData) {

        try {

            Author author = authorRepository.findById(blogData.getAuthor_id()).orElseThrow(() -> new ResourceNotFoundException("Author", "id", blogData.getAuthor_id()));
            Categories categories = categoriesRepository.findById(blogData.getCategories_id()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", blogData.getCategories_id()));

            List<String> tagtag = blogData.getTags_name();
            ArrayList<Tags> tags = new ArrayList<Tags>();

            for (String tag : tagtag) {
                Tags val = tagRepository.findByName(tag);

                if(val == null) {
                    Tags newtag = new Tags();

                    newtag.setName(tag);
                    tagRepository.save(newtag);

                    Tags tagId = tagRepository.findById(newtag.getId()).orElseThrow(() -> new ResourceNotFoundException("Tags", "id", newtag.getId()));
                    tags.add(tagId);
                } else {
                        
                    tags.add(val);
                
                }
                
            }
            
            Blog blog = new Blog();
            blog.setAuthor(author);
            blog.setCategories(categories);
            blog.setTag(tags);

            BeanUtils.copyProperties(blogData, blog);
         
            return blogRepository.save(blog);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Blog update(Long id, RequestUpdateBlogDto request) {

        try {

            Blog blog = blogRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));
            
            BeanUtils.copyProperties(request, blog);
            blog.setUpdated_at(dateTime.getCurrentDate());
            blogRepository.save(blog);
            return blog;

        } catch (ResourceNotFoundException e) {

            log.error(e.getMessage(), e);
            throw e;

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    private ResponseBlogDto fromEntity(Blog blog) {

        ResponseBlogDto response = new ResponseBlogDto();
        BeanUtils.copyProperties(blog, response);
        return response;
        
    }

}