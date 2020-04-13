package com.example.temanbelajar.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.temanbelajar.config.pagination.ConfigPage;
import com.example.temanbelajar.config.pagination.ConfigPageable;
import com.example.temanbelajar.config.pagination.PageConverter;

import com.example.temanbelajar.dto.ResponseBaseDto;
import com.example.temanbelajar.dto.ResponsePagination;
import com.example.temanbelajar.dto.request.RequestCategoriesDto;
import com.example.temanbelajar.dto.response.ResponseCategoriesDto;
import com.example.temanbelajar.model.Categories;
import com.example.temanbelajar.service.CategoriesService;

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
 * CategoriesController
 */
@RestController
@RequestMapping("/category")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;


    // @GetMapping("/")
    // public ResponseEntity<ResponseBaseDto> getAllCategoriesPagination(Pageable pageable) {
        
    //     ResponseBaseDto response = new ResponseBaseDto();

    //     try {

    //         Page<Categories> categories = categoriesRepository.findAll();
    //         response.setData(categories);

    //         return new ResponseEntity<>(response, HttpStatus.OK);

    //     } catch (Exception e) {

    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    //     }

    // }

    @GetMapping()
    public ResponsePagination<ConfigPage<ResponseCategoriesDto>> getAllCategories(ConfigPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request){
        
        try {

            Page<ResponseCategoriesDto> categories;

            if (param != null) {
                categories = categoriesService.findByNameParams(ConfigPageable.convertToPageable(pageable), param);
            } else {
                categories = categoriesService.findAll(ConfigPageable.convertToPageable(pageable));
            }

            PageConverter<ResponseCategoriesDto> converter = new PageConverter<>();
            String url = String.format("%s://%s:%d/category",request.getScheme(),  request.getServerName(), request.getServerPort());

            String search = "";

            if(param != null){
                search += "&param="+param;
            }

            ConfigPage<ResponseCategoriesDto> respon = converter.convert(categories, url, search);

            return ResponsePagination.ok(respon);

        } catch (Exception e) {

            return ResponsePagination.error(200, e.getMessage());

        }

    }

    @GetMapping("/{id}")
    public ResponseBaseDto<ResponseCategoriesDto> detailCategory(@PathVariable(value = "id") Long categoryId) {

        try {

            return ResponseBaseDto.ok(categoriesService.findById(categoryId));

        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }

    }


    @PostMapping()
    public ResponseBaseDto createCategory(@RequestBody RequestCategoriesDto categoriesData) {
       
        try {

            Categories categories = categoriesService.save(categoriesData);

            return ResponseBaseDto.saved(categories);
            
        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }

    }

    @PutMapping("{id}")
    public ResponseBaseDto updateCategory(@PathVariable(value = "id") Long categoryId, @RequestBody RequestCategoriesDto categoriesData) {
                
            try {

                Categories categories = categoriesService.update(categoryId, categoriesData);

                return ResponseBaseDto.saved(categories);
                
            } catch (Exception e) {

                return ResponseBaseDto.error(400, e.getMessage());

            }
    }


    // @DeleteMapping("{id}")
    // public ResponseEntity<ResponseBaseDto> deleteCategory(@PathVariable(value = "id") Long categoryId) {

    //     ResponseBaseDto response = new ResponseBaseDto();

    //     Categories category = categoriesRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

    //     try {

    //         //response.setData(categoriesRepository.deleteById(categoryId));
    //         categoriesRepository.deleteById(categoryId);

    //         return new ResponseEntity<>(response, HttpStatus.OK);

    //     } catch (Exception e) {

    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    //     }
    // }

    @DeleteMapping("/")
    public ResponseBaseDto deleteCategoryRequest(@RequestBody Categories categoriesData) {

        try {

            categoriesService.deleteById(categoriesData.getId());

            return ResponseBaseDto.ok();

        } catch (Exception e) {

            return ResponseBaseDto.error(400, e.getMessage());

        }
    }

    

    
}