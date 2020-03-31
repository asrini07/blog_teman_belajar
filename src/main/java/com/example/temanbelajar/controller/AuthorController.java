package com.example.temanbelajar.controller;

import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.dto.request.AuthorDto;
import com.example.temanbelajar.dto.request.AuthorPassDto;
import com.example.temanbelajar.exeption.ResourceNotFoundException;
import com.example.temanbelajar.model.Author;
import com.example.temanbelajar.repository.AuthorRepository;
import com.example.temanbelajar.service.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/")
    public ResponseEntity<ResponseBaseDto> getAllAuthor() {

        ResponseBaseDto response = new ResponseBaseDto();

        try {

            //Page<Author> author = authorRepository.findAll(pageable);
            response.setData(authorRepository.findAll());

            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        
        }
    }

    @PostMapping("/")
    public ResponseEntity<ResponseBaseDto> createAuthor(@RequestBody Author authorData){

        ResponseBaseDto response = new ResponseBaseDto();

        try {
            
            response.setData(authorRepository.save(authorData));

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
           // author.setPassword(authorDto.getPassword());

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



    
}