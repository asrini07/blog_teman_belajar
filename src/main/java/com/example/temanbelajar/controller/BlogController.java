package com.example.temanbelajar.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.example.temanbelajar.config.pagination.ConfigPage;
import com.example.temanbelajar.config.pagination.ConfigPageable;
import com.example.temanbelajar.config.pagination.PageConverter;
import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.dto.request.BlogRequestDto;
import com.example.temanbelajar.dto.request.BlogRequestUpdateDto;
import com.example.temanbelajar.dto.response.BlogResponseDto;
import com.example.temanbelajar.dto.response.UploadFileResponse;
import com.example.temanbelajar.model.Blog;
import com.example.temanbelajar.repository.AuthorRepository;
import com.example.temanbelajar.repository.BlogRepository;
import com.example.temanbelajar.repository.CategoriesRepository;
import com.example.temanbelajar.repository.TagRepository;
import com.example.temanbelajar.service.BlogService;
import com.example.temanbelajar.service.FileStorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping()
    public ResponseBaseDto<ConfigPage<BlogResponseDto>> getAllBlog(ConfigPageable pageable, @RequestParam(required = false) String param,
     @RequestParam(required = false) Long categories_id, @RequestParam(required = false) Long author_id, 
     @RequestParam(required = false) String tag_name,  HttpServletRequest request){

        try {

            Page<BlogResponseDto> blogs;

            if (param != null) {
                blogs = blogService.findByNameParams(ConfigPageable.convertToPageable(pageable), param);
            } else if (author_id != null) {
                blogs = blogService.findByAuthor(ConfigPageable.convertToPageable(pageable), author_id);
            } else if (categories_id != null) {
                blogs = blogService.findByCategory(ConfigPageable.convertToPageable(pageable), categories_id);
            } else if (tag_name != null) {
                blogs = blogService.findByTag(ConfigPageable.convertToPageable(pageable), tag_name);
            } else {
                blogs = blogService.findAll(ConfigPageable.convertToPageable(pageable));
            }

            PageConverter<BlogResponseDto> converter = new PageConverter<>();
            String url = String.format("%s://%s:%d/blog",request.getScheme(),  request.getServerName(), request.getServerPort());

            String search = "";

            if(param != null) {
                search += "&param="+param;
            } else if (author_id != null) {
                search += "&author_id="+author_id;
            } else if (categories_id != null) {
                search += "&category_id="+categories_id;
            } else if (tag_name != null) {
                search += "&tag_name="+tag_name;
            }

            ConfigPage<BlogResponseDto> respon = converter.convert(blogs, url, search);

            return ResponseBaseDto.ok(respon);


        } catch (Exception e) {

            return ResponseBaseDto.error(200, e.getMessage());
        
        }
    }


    @PostMapping()
    public ResponseBaseDto createBlog(@RequestBody BlogRequestDto blogData) {

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
    public ResponseBaseDto<BlogResponseDto> detailBlog(@PathVariable(value = "id") Long blogId) {

        try {

            return ResponseBaseDto.ok(blogService.findById(blogId));

        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }

    }

    @PutMapping("{id}")
    public ResponseBaseDto updateBlog(@PathVariable(value = "id") Long blogId, @RequestBody BlogRequestUpdateDto blogData) {

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


    @PutMapping("/updateFile/{id}")
    public String updateFile(@RequestParam("image") MultipartFile file, @PathVariable(value = "id") Long blogId) {

            String blog = blogService.updateFileBlog(file, blogId);

            return blog;

    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {

        //Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    
}