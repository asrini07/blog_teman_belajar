package com.example.temanbelajar.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.temanbelajar.config.pagination.ConfigPage;
import com.example.temanbelajar.config.pagination.ConfigPageable;
import com.example.temanbelajar.config.pagination.PageConverter;
import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.dto.ResponsePagination;
import com.example.temanbelajar.dto.request.RequestAuthorDto;
import com.example.temanbelajar.dto.request.RequestAuthorPassDto;
import com.example.temanbelajar.dto.request.RequestUpdateAuthorDto;
import com.example.temanbelajar.dto.response.ResponseAuthorDto;
import com.example.temanbelajar.exeption.ResourceNotFoundException;
import com.example.temanbelajar.model.Author;
import com.example.temanbelajar.repository.AuthorRepository;
import com.example.temanbelajar.service.AuthorService;

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
 * AuthorController
 */
@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping()
    public ResponsePagination<ConfigPage<ResponseAuthorDto>> getAllCategories(ConfigPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request){
        
        try {

            Page<ResponseAuthorDto> author;

            if (param != null) {
                author = authorService.findByNameParams(ConfigPageable.convertToPageable(pageable), param);
            } else {
                author = authorService.findAll(ConfigPageable.convertToPageable(pageable));
            }

            PageConverter<ResponseAuthorDto> converter = new PageConverter<>();
            String url = String.format("%s://%s:%d/author",request.getScheme(),  request.getServerName(), request.getServerPort());

            String search = "";

            if(param != null){
                search += "&param="+param;
            }

            ConfigPage<ResponseAuthorDto> respon = converter.convert(author, url, search);

            return ResponsePagination.ok(respon);

        } catch (Exception e) {

            return ResponsePagination.error(200, e.getMessage());

        }

    }

    @PostMapping()
    public ResponseBaseDto createAuthor(@RequestBody RequestAuthorDto authorData){

        try {

            Author author = authorService.save(authorData);

            return ResponseBaseDto.saved(author);
            
        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }

    }

    @GetMapping("/{id}")
    public ResponseBaseDto<ResponseAuthorDto> detailAuthor(@PathVariable(value = "id") Long authorId) {

        try {

            return ResponseBaseDto.ok(authorService.findById(authorId));

        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }

    }

    @PutMapping("{id}")
    public ResponseBaseDto updateAuthor(@PathVariable(value = "id") Long authorId, @RequestBody RequestUpdateAuthorDto authorData) {

        try {

            Author author = authorService.update(authorId, authorData);

            return ResponseBaseDto.saved(author);
            
        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }

    }

    @PutMapping("{id}/password")
    public ResponseBaseDto updatePassword(@PathVariable(value = "id") Long authorId, @RequestBody RequestAuthorPassDto authorPassDto) {

        try {

            Author author = authorService.changePassword(authorId, authorPassDto);

            return ResponseBaseDto.saved(author);
            
        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }

    }

    @DeleteMapping("/")
    public ResponseBaseDto deleteAuthorRequest(@RequestBody Author authorData) {

        try {

            authorService.deleteById(authorData.getId());

            return ResponseBaseDto.ok();

        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }

    }

    
}