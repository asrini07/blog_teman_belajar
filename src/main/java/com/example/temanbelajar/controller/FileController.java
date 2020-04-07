package com.example.temanbelajar.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.dto.response.UploadFileResponse;
import com.example.temanbelajar.exeption.ResourceNotFoundException;
import com.example.temanbelajar.model.Author;
import com.example.temanbelajar.model.Blog;
import com.example.temanbelajar.model.Categories;
import com.example.temanbelajar.model.Tags;
import com.example.temanbelajar.repository.AuthorRepository;
import com.example.temanbelajar.repository.CategoriesRepository;
import com.example.temanbelajar.repository.TagRepository;
import com.example.temanbelajar.service.BlogService;
import com.example.temanbelajar.service.FileStorageService;
import com.example.temanbelajar.service.TagService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

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

    @PostMapping("/uploadFile")
    public ResponseEntity<ResponseBaseDto> uploadFile(@RequestParam("image") MultipartFile file, @RequestParam Long author_id, @RequestParam Long categories_id, @RequestParam("tags_name") String tags_name, @RequestParam String title, @RequestParam String content) {
        ResponseBaseDto response = new ResponseBaseDto();

        Author author = authorRepository.findById(author_id).orElseThrow(() -> new ResourceNotFoundException("Author", "id", author_id));
        Categories categories = categoriesRepository.findById(categories_id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categories_id));

        // List<String> tagtag = tags_name;
        // ArrayList<Tags> tags = new ArrayList<Tags>();

        // for (String tag : tagtag) {
        //     Tags val = tagRepository.findByName(tag);

        //     if(val == null) {
        //         Tags newtag = new Tags();

        //         newtag.setName(tag);
        //         tagRepository.save(newtag);

        //         Tags tagId = tagRepository.findById(newtag.getId()).orElseThrow(() -> new ResourceNotFoundException("Tags", "id", newtag.getId()));
        //         tags.add(tagId);
        //     } else {
                    
        //         tags.add(val);
            
        //     }
            
        // }

        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize(), file.getName());
    }

    // @PostMapping("/uploadFile")
    // public UploadFileResponse uploadFile(@RequestParam("image") MultipartFile file) {
    //     String fileName = fileStorageService.storeFile(file);

    //     String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
    //             .path("/downloadFile/")
    //             .path(fileName)
    //             .toUriString();

    //     return new UploadFileResponse(fileName, fileDownloadUri,
    //             file.getContentType(), file.getSize(), file.getName());
    // }

    // @PostMapping("/uploadMultipleFiles")
    // public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
    //     return Arrays.asList(files)
    //             .stream()
    //             .map(file -> uploadFile(file))
    //             .collect(Collectors.toList());
    // }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
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