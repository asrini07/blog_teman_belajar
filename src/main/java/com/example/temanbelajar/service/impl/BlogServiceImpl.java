package com.example.temanbelajar.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.temanbelajar.config.DateTime;
import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.dto.request.BlogRequestDto;
import com.example.temanbelajar.dto.request.BlogRequestUpdateDto;
import com.example.temanbelajar.dto.response.BlogResponseDto;
import com.example.temanbelajar.dto.response.UploadResponseDto;
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
import com.example.temanbelajar.service.FileStorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlogServiceImpl implements BlogService {

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

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public Page<BlogResponseDto> findAll(Pageable pageable) {

        try {

            return blogRepository.findAll(pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }

    }

    @Override
    public BlogResponseDto findById(Long id) {

        try {

            Blog blog = blogRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));

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
    public Page<BlogResponseDto> findByNameParams(Pageable pageable, String param) {

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

            Blog blog = blogRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));

            blogRepository.deleteById(id);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Blog save(BlogRequestDto blogData) {

        try {

            Author author = authorRepository.findById(blogData.getAuthor_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Author", "id", blogData.getAuthor_id()));
            Categories categories = categoriesRepository.findById(blogData.getCategories_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", blogData.getCategories_id()));

            List<String> tagtag = blogData.getTags_name();
            ArrayList<Tags> tags = new ArrayList<Tags>();

            for (String tag : tagtag) {
                Tags val = tagRepository.findByName(tag);

                if (val == null) {
                    Tags newtag = new Tags();

                    newtag.setName(tag);
                    tagRepository.save(newtag);

                    Tags tagId = tagRepository.findById(newtag.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Tags", "id", newtag.getId()));
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
    public Blog update(Long id, BlogRequestUpdateDto request) {

        try {

            Blog blog = blogRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));

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

    private BlogResponseDto fromEntity(Blog blog) {

        BlogResponseDto response = new BlogResponseDto();
        BeanUtils.copyProperties(blog, response);
        return response;

    }

    private UploadResponseDto fromEntityUpload(Blog blog) {

        UploadResponseDto response = new UploadResponseDto();
        BeanUtils.copyProperties(blog, response);
        return response;

    }

    @Override
    public Page<BlogResponseDto> findByAuthor(Pageable pageable, Long author_id) {

        try {
            return blogRepository.findByAuthor(pageable, author_id).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public Page<BlogResponseDto> findByCategory(Pageable pageable, Long categories_id) {

        try {
            return blogRepository.findByCategory(pageable, categories_id).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public Page<BlogResponseDto> findByTag(Pageable pageable, String tag_name) {

        try {
            return blogRepository.findByTag(pageable, tag_name).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public String updateFileBlog(MultipartFile file, Long blogId) {
        try {

            Blog blog = blogRepository.findById(blogId)
                    .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, FIELD, blogId.toString()));

            String fileName = fileStorageService.storeFile(file);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
                    .path(fileName).toUriString();

            blog.setImageUrl(fileName);
            blog.setUpdated_at(dateTime.getCurrentDate());

            // blogRepository.save(author);
            blogRepository.save(blog);
            // return
            return fileDownloadUri;
            //return blogRepository.save(blog);

        } catch (ResourceNotFoundException e) {

            log.error(e.getMessage(), e);
            throw e;

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    // @Override
    // public ResponseEntity<Resource> downloadFileBlog(String fileName, HttpServletRequest request) {
        
    //     try {

    //         Resource resource = fileStorageService.loadFileAsResource(fileName);

    //         // Try to determine file's content type
    //         String contentType = null;
    //         try {
    //             contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    //         } catch (IOException ex) {
    //             LOGGER.info("Could not determine file type.");
    //         }

    //         // Fallback to the default content type if type could not be determined
    //         if(contentType == null) {
    //             contentType = "application/octet-stream";
    //         }

    //         return ResponseEntity.ok()
    //             .contentType(MediaType.parseMediaType(contentType))
    //             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
    //             .body(resource);

    //         // Blog blog = blogRepository.findById(id)
    //         //         .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));

    //         // blogRepository.deleteById(id);

    //     } catch (Exception e) {

    //         log.error(e.getMessage(), e);
    //         throw e;

    //     }

    // }

}