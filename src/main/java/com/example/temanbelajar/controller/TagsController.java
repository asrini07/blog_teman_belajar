package com.example.temanbelajar.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.temanbelajar.config.pagination.ConfigPage;
import com.example.temanbelajar.config.pagination.ConfigPageable;
import com.example.temanbelajar.config.pagination.PageConverter;
import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.dto.ResponsePagination;
import com.example.temanbelajar.exeption.ResourceNotFoundException;
import com.example.temanbelajar.model.Tags;
import com.example.temanbelajar.repository.TagRepository;
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
 * TagsController
 */
@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TagService tagService;

    //with pagination
    @GetMapping()
    public ResponsePagination<ConfigPage<Tags>> getAllTag(ConfigPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request){

        try {

            Page<Tags> tags;

            if (param != null) {
                tags = tagService.findByNameParams(ConfigPageable.convertToPageable(pageable), param);
            } else {
                tags = tagService.findAll(ConfigPageable.convertToPageable(pageable));
            }

            PageConverter<Tags> converter = new PageConverter<>();
            String url = String.format("%s://%s:%d/tags",request.getScheme(),  request.getServerName(), request.getServerPort());

            String search = "";

            if(param != null){
                search += "&param="+param;
            }

            ConfigPage<Tags> respon = converter.convert(tags, url, search);

            return ResponsePagination.ok(respon);
            
        } catch (Exception e) {

            return ResponsePagination.error("200", e.getMessage());
            
        }
    }

    // @GetMapping("/")
    // public ResponseEntity<ResponseBaseDto> getAllTag(){

    //     ResponseBaseDto response = new ResponseBaseDto();

    //     try {

    //        // Page<Tags> tags = tagReporitory.findAll(pageable);
    //         response.setData(tagRepository.findAll());

    //         return new ResponseEntity<>(response, HttpStatus.OK);
            
    //     } catch (Exception e) {
            
    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
            
    //     }
    // }

    @PostMapping()
    public ResponseEntity<ResponseBaseDto> createTag(@RequestBody Tags tags){

        ResponseBaseDto response = new ResponseBaseDto();

        try {

            response.setData(tagRepository.save(tags));
            return new ResponseEntity<>(response ,HttpStatus.OK);
            
        } catch (Exception e) {
            
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBaseDto> detailTags(@PathVariable(value = "id") Long tagId) {

        ResponseBaseDto response = new ResponseBaseDto();

        try {
            
            Tags tags = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
            
            response.setData(tags);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseBaseDto> deleteTags(@PathVariable(value = "id") Long tagId) {

        ResponseBaseDto response = new ResponseBaseDto();

        Tags tags = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

        try {
            
            tagRepository.deleteById(tagId);

            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
            
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseBaseDto> updateCategory(@PathVariable(value = "id") Long tagId, @RequestBody Tags tagData) {

        ResponseBaseDto response = new ResponseBaseDto();

        Tags tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

        try {

            Tags updateTags = tagService.update(tagId, tagData);
                
            response.setData(updateTags);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<ResponseBaseDto> deleteTagsRequest(@RequestBody Tags tagData) {

        ResponseBaseDto response = new ResponseBaseDto();

        Tags tags = tagRepository.findById(tagData.getId()).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagData.getId()));

        try {
            
            tagRepository.deleteById(tagData.getId());

            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
            
        }
    }



}