package com.example.temanbelajar.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.temanbelajar.config.pagination.ConfigPage;
import com.example.temanbelajar.config.pagination.ConfigPageable;
import com.example.temanbelajar.config.pagination.PageConverter;
import com.example.temanbelajar.dto.ResponseBaseDto;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping()
    public ResponseBaseDto<ConfigPage<ResponseBlogDto>> getAllBlog(ConfigPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request){

        try {

            Page<ResponseBlogDto> blogs;

            if (param != null) {
                blogs = blogService.findByNameParams(ConfigPageable.convertToPageable(pageable), param);
            } else {
                blogs = blogService.findAll(ConfigPageable.convertToPageable(pageable));
            }

            PageConverter<ResponseBlogDto> converter = new PageConverter<>();
            String url = String.format("%s://%s:%d/blog",request.getScheme(),  request.getServerName(), request.getServerPort());

            String search = "";

            if(param != null){
                search += "&param="+param;
            }

            ConfigPage<ResponseBlogDto> respon = converter.convert(blogs, url, search);

            return ResponseBaseDto.ok(respon);


        } catch (Exception e) {

            return ResponseBaseDto.error(200, e.getMessage());
        
        }
    }

    @PostMapping()
    public ResponseBaseDto createBlog(@RequestBody RequestBlogDto blogData) {

        try {

            Blog blog = blogService.save(blogData);

            return ResponseBaseDto.saved(blog);
            
        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }

    }

    //by ID
    // @PostMapping("/save")
    // public ResponseEntity<ResponseBaseDto> createBlogTagId(@RequestBody Blog blogData) {

    //     ResponseBaseDto response = new ResponseBaseDto();

    //     Author author = authorRepository.findById(blogData.getAuthor_id()).orElseThrow(() -> new ResourceNotFoundException("Author", "id", blogData.getAuthor_id()));
    //     Categories categories = categoriesRepository.findById(blogData.getCategories_id()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", blogData.getCategories_id()));

    //     List<Long> tagtag = blogData.getTags_id();
    //     ArrayList<Tags> tags = new ArrayList<Tags>();

    //     for (Long tag : tagtag) {
    //         Tags val = tagRepository.findById(tag).orElseThrow(() -> new ResourceNotFoundException("Tags", "id", tag));
    //         tags.add(val);
    //     }

    //     blogData.setAuthor(author);
    //     blogData.setCategories(categories);
    //     blogData.setTag(tags);

    //     try {

    //         response.setData(blogRepository.save(blogData));

    //         return new ResponseEntity<>(response ,HttpStatus.OK);
            
    //     } catch (Exception e) {

    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

    //     }

    // }

    @GetMapping("/{id}")
    public ResponseBaseDto<ResponseBlogDto> detailBlog(@PathVariable(value = "id") Long blogId) {

        try {

            return ResponseBaseDto.ok(blogService.findById(blogId));

        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }

    }

    @PutMapping("{id}")
    public ResponseBaseDto updateBlog(@PathVariable(value = "id") Long blogId, @RequestBody RequestUpdateBlogDto blogData) {

        try {

            Blog blog = blogService.update(blogId, blogData);

            return ResponseBaseDto.saved(blog);
            
        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }

    }

    @DeleteMapping("/")
    public ResponseBaseDto deleteBlogRequest(@RequestBody Blog blogData) {

        try {

            blogService.deleteById(blogData.getId());

            return ResponseBaseDto.ok();

        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }

    }

    private final Logger logger = LoggerFactory.getLogger(BlogController.class);

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/Users/aasasrini/Documents/vide";

    // 3.1.1 Single file upload
    @PostMapping("/upload")
    // If not @RestController, uncomment this
    //@ResponseBody
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile uploadfile) {

        logger.debug("Single file upload!");

        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {

            saveUploadedFiles(Arrays.asList(uploadfile));

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);

    }

 

    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

        }

    }
    
    
}