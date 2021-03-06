package com.example.temanbelajar.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.temanbelajar.config.pagination.ConfigPage;
import com.example.temanbelajar.config.pagination.ConfigPageable;
import com.example.temanbelajar.config.pagination.PageConverter;
import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.dto.request.TagRequestDto;
import com.example.temanbelajar.dto.response.TagResponseDto;
import com.example.temanbelajar.model.Tags;
import com.example.temanbelajar.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    TagService tagService;

    //with pagination
    @GetMapping()
    public ResponseBaseDto<ConfigPage<TagResponseDto>> getAllTag(ConfigPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request){

        try {

            Page<TagResponseDto> tags;

            if (param != null) {
                tags = tagService.search(ConfigPageable.convertToPageable(pageable), param);
            } else {
                tags = tagService.findAll(ConfigPageable.convertToPageable(pageable));
            }

            PageConverter<TagResponseDto> converter = new PageConverter<>();
            String url = String.format("%s://%s:%d/tags",request.getScheme(),  request.getServerName(), request.getServerPort());

            String search = "";

            if(param != null){
                search += "&param="+param;
            }

            ConfigPage<TagResponseDto> response = converter.convert(tags, url, search);

            return ResponseBaseDto.ok(response);
            
        } catch (Exception e) {

            return ResponseBaseDto.error("200", e.getMessage());
            
        }
    }

    @PostMapping()
    public ResponseBaseDto createTag(@RequestBody TagRequestDto request){

        try {

            Tags tags = tagService.save(request);

            return ResponseBaseDto.saved(tags);
            
        } catch (Exception e) {
            
            return ResponseBaseDto.error("400", e.getMessage());

        }

    }

    @GetMapping("/{id}")
    public ResponseBaseDto<TagResponseDto> detailTags(@PathVariable(value = "id") Long tagId) {

        try {

            return ResponseBaseDto.ok(tagService.findById(tagId));

        } catch (Exception e) {

            return ResponseBaseDto.error("400", e.getMessage());

        }
    }

    // @DeleteMapping("{id}")
    // public ResponseEntity<ResponseBaseDto> deleteTags(@PathVariable(value = "id") Long tagId) {

    //     ResponseBaseDto response = new ResponseBaseDto();

    //     Tags tags = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

    //     try {
            
    //         tagRepository.deleteById(tagId);

    //         return new ResponseEntity<>(response, HttpStatus.OK);
            
    //     } catch (Exception e) {

    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
            
    //     }
    // }

    @PutMapping("{id}")
    public ResponseBaseDto updateCategory(@PathVariable(value = "id") Long tagId, @RequestBody TagRequestDto tagData) {

        try {

            Tags tags = tagService.update(tagId, tagData);

            return ResponseBaseDto.saved(tags);
            
        } catch (Exception e) {

            return ResponseBaseDto.error("400", e.getMessage());

        }
    }

    @DeleteMapping("/")
    public ResponseBaseDto deleteTagsRequest(@RequestBody Tags tagData) {

        try {
            
            tagService.deleteById(tagData.getId());

            return ResponseBaseDto.ok();
            
        } catch (Exception e) {

            return ResponseBaseDto.error("400", e.getMessage());
            
        }
    }

}