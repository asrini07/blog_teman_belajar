package com.example.temanbelajar.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.temanbelajar.config.pagination.ConfigPage;
import com.example.temanbelajar.config.pagination.ConfigPageable;
import com.example.temanbelajar.config.pagination.PageConverter;
import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.dto.ResponsePagination;
import com.example.temanbelajar.dto.request.AuthorDto;
import com.example.temanbelajar.dto.request.AuthorPassDto;
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
    AuthorRepository authorRepository;

    @Autowired
    AuthorService authorService;

    @GetMapping()
    public ResponsePagination<ConfigPage<Author>> getAllAuthor(ConfigPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request){

        try {

            Page<Author> author;

            if (param != null) {
                author = authorService.findByNameParams(ConfigPageable.convertToPageable(pageable), param);
            } else {
                author = authorService.findAll(ConfigPageable.convertToPageable(pageable));
            }

            PageConverter<Author> converter = new PageConverter<>();
            String url = String.format("%s://%s:%d/author",request.getScheme(),  request.getServerName(), request.getServerPort());

            String search = "";

            if(param != null){
                search += "&param="+param;
            }

            ConfigPage<Author> respon = converter.convert(author, url, search);

            return ResponsePagination.ok(respon);
            
        } catch (Exception e) {

            return ResponsePagination.error(200, e.getMessage());
        
        }
    }

    // @GetMapping("/")
    // public ResponseEntity<ResponseBaseDto> getAllAuthor() {

    //     ResponseBaseDto response = new ResponseBaseDto();

    //     try {

    //         //Page<Author> author = authorRepository.findAll(pageable);
    //         response.setData(authorRepository.findAll());

    //         return new ResponseEntity<>(response, HttpStatus.OK);
            
    //     } catch (Exception e) {

    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        
    //     }
    // }

    @PostMapping()
    public ResponseEntity<ResponseBaseDto> createAuthor(@RequestBody Author authorData){

        ResponseBaseDto response = new ResponseBaseDto();

        try {
            
            response.setData(authorService.save(authorData));

            return new ResponseEntity<>(response ,HttpStatus.OK);

        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage() + "opppppp");

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBaseDto> detailAuthor(@PathVariable(value = "id") Long authorId) {

        ResponseBaseDto response = new ResponseBaseDto();

        try {

            Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));

            response.setData(author);

            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseBaseDto> deleteAuthor(@PathVariable(value = "id") Long authorId) {

        ResponseBaseDto response = new ResponseBaseDto();

        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));

        try {

            authorRepository.deleteById(authorId);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseBaseDto> updateAuthor(@PathVariable(value = "id") Long authorId, @RequestBody AuthorDto authorDto) {

        ResponseBaseDto response = new ResponseBaseDto();

        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));

        try {
            author.setFirst_name(authorDto.getNama_depan());
            author.setLast_name(authorDto.getNama_belakang());
            author.setUsername(authorDto.getUsername());
            //Author updateAuthor = authorService.update(authorId, authorData);
                
            response.setData(authorService.update(authorId, author));

            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }
    }

    @PutMapping("{id}/password")
    public ResponseEntity<ResponseBaseDto> updatePassword(@PathVariable(value = "id") Long authorId, @RequestBody AuthorPassDto authorPassDto) {

        ResponseBaseDto response = new ResponseBaseDto();

        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));

        try {
            author.setPassword(authorPassDto.getPassword());

            Author updatePassword = authorService.changePassword(authorId, author);
                
            response.setData(updatePassword);

            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }
    }

    @DeleteMapping("/")
    public ResponseEntity<ResponseBaseDto> deleteAuthorRequest(@RequestBody Author authorData) {

        ResponseBaseDto response = new ResponseBaseDto();

        Author author = authorRepository.findById(authorData.getId()).orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorData.getId()));

        try {

            authorRepository.deleteById(authorData.getId());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }
    }



    
}