package com.example.temanbelajar.controller;

import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.exeption.ResourceNotFoundException;
import com.example.temanbelajar.model.Categories;
import com.example.temanbelajar.repository.CategoriesRepository;
import com.example.temanbelajar.service.CategoriesService;

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
 * CategoriesController
 */
@RestController
@RequestMapping("/category")
public class CategoriesController {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/")
    public ResponseEntity<ResponseBaseDto> getAllCategories() {
        
        ResponseBaseDto response = new ResponseBaseDto();

        try {

           // Page<Categories> categories = categoryRepository.findAll(pageable);
            response.setData(categoriesRepository.findAll());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseBaseDto> detailCategory(@PathVariable(value = "id") Long categoryId) {

        ResponseBaseDto response = new ResponseBaseDto();

        try {

            Categories categories = categoriesRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
            
            response.setData(categories);

            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }


    @PostMapping("/")
    public ResponseEntity<ResponseBaseDto> createCategory(@RequestBody Categories categories) {
       
        ResponseBaseDto response = new ResponseBaseDto();

        try {

            response.setData(categoriesRepository.save(categories));

            return new ResponseEntity<>(response ,HttpStatus.OK);
            
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }

    }


    @PutMapping("{id}")
    public ResponseEntity<ResponseBaseDto> updateCategory(@PathVariable(value = "id") Long categoryId, @RequestBody Categories categoriesData) {

        ResponseBaseDto response = new ResponseBaseDto();

        Categories category = categoriesRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
                
            try {

                Categories updateCategories = categoriesService.update(categoryId, categoriesData);
                
                response.setData(updateCategories);

                return new ResponseEntity<>(response, HttpStatus.OK);
                
            } catch (Exception e) {

                response.setStatus(false);
                response.setCode(500);
                response.setMessage(e.getMessage());

                return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

            }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<ResponseBaseDto> deleteCategory(@PathVariable(value = "id") Long categoryId) {

        ResponseBaseDto response = new ResponseBaseDto();

        Categories category = categoriesRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        try {

            //response.setData(categoriesRepository.deleteById(categoryId));
            categoriesRepository.deleteById(categoryId);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    

    
}