package com.example.temanbelajar.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.temanbelajar.config.pagination.ConfigPage;
import com.example.temanbelajar.config.pagination.ConfigPageable;
import com.example.temanbelajar.config.pagination.PageConverter;
import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.dto.ResponsePagination;
import com.example.temanbelajar.dto.request.BlogDto;
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
import com.example.temanbelajar.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * BlogController
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    CategoriesRepository categoriesRepository;
    
    @Autowired
    TagRepository tagRepository;

    @Autowired
    BlogService blogService;

    @Autowired
    TagService tagService;

    @GetMapping()
    public ResponsePagination<ConfigPage<Blog>> getAllBlog(ConfigPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request){

        try {

            Page<Blog> blogs;

            if (param != null) {
                blogs = blogService.findByNameParams(ConfigPageable.convertToPageable(pageable), param);
            } else {
                blogs = blogService.findAll(ConfigPageable.convertToPageable(pageable));
            }

            PageConverter<Blog> converter = new PageConverter<>();
            String url = String.format("%s://%s:%d/blog",request.getScheme(),  request.getServerName(), request.getServerPort());

            String search = "";

            if(param != null){
                search += "&param="+param;
            }

            ConfigPage<Blog> respon = converter.convert(blogs, url, search);

            return ResponsePagination.ok(respon);


        } catch (Exception e) {

            return ResponsePagination.error("200", e.getMessage());
        
        }
    }

    // @GetMapping("/")
    // public ResponseEntity<ResponseBaseDto> getAllBlog() {

    //     ResponseBaseDto response = new ResponseBaseDto();

    //     try {
            
    //         response.setData(blogRepository.findAll());

    //         return new ResponseEntity<>(response, HttpStatus.OK);


    //     } catch (Exception e) {

    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        
    //     }
    // }

    @PostMapping()
    public ResponseEntity<ResponseBaseDto> createBlog(@RequestBody Blog blogData) {

        ResponseBaseDto response = new ResponseBaseDto();

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

        blogData.setAuthor(author);
        blogData.setCategories(categories);
        blogData.setTag(tags);

        try {

            response.setData(blogRepository.save(blogData));

            return new ResponseEntity<>(response ,HttpStatus.OK);
            
        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }

    }

    //by ID
    @PostMapping("/save")
    public ResponseEntity<ResponseBaseDto> createBlogTagId(@RequestBody Blog blogData) {

        ResponseBaseDto response = new ResponseBaseDto();

        Author author = authorRepository.findById(blogData.getAuthor_id()).orElseThrow(() -> new ResourceNotFoundException("Author", "id", blogData.getAuthor_id()));
        Categories categories = categoriesRepository.findById(blogData.getCategories_id()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", blogData.getCategories_id()));

        List<Long> tagtag = blogData.getTags_id();
        ArrayList<Tags> tags = new ArrayList<Tags>();

        for (Long tag : tagtag) {
            Tags val = tagRepository.findById(tag).orElseThrow(() -> new ResourceNotFoundException("Tags", "id", tag));
            tags.add(val);
        }

        blogData.setAuthor(author);
        blogData.setCategories(categories);
        blogData.setTag(tags);

        try {

            response.setData(blogRepository.save(blogData));

            return new ResponseEntity<>(response ,HttpStatus.OK);
            
        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBaseDto> detailBlog(@PathVariable(value = "id") Long blogId) {

        ResponseBaseDto response = new ResponseBaseDto();

        try {

            Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));
            
            response.setData(blog);

            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {
            
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseBaseDto> deleteBlog(@PathVariable(value = "id") Long blogId) {

        ResponseBaseDto response = new ResponseBaseDto();

        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));

        try {
            
            blogRepository.deleteById(blogId);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }

    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseBaseDto> updateBlog(@PathVariable(value = "id") Long blogId, @RequestBody Blog blogData) {

        ResponseBaseDto response = new ResponseBaseDto();

        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));

        try {
            
            blog.setTitle(blogData.getTitle());
            blog.setContent(blogData.getContent());
            
            response.setData(blogService.update(blogId, blog));

            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {
            
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }

    }

    @DeleteMapping("/")
    public ResponseEntity<ResponseBaseDto> deleteBlogRequest(@RequestBody Blog blogData) {

        ResponseBaseDto response = new ResponseBaseDto();

        Blog blog = blogRepository.findById(blogData.getId()).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogData.getId()));

        try {
            
            blogRepository.deleteById(blogData.getId());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }

    }
    
    
}