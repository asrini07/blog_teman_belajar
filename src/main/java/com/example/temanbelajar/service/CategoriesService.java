package com.example.temanbelajar.service;

import com.example.temanbelajar.model.Categories;
import com.example.temanbelajar.dto.request.RequestCategoriesDto;
import com.example.temanbelajar.dto.response.ResponseCategoriesDto;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * TagService
 */
public interface CategoriesService {

    Page<ResponseCategoriesDto> findAll(Pageable pageable);

    ResponseCategoriesDto findById(Long categoryId);

    Page<ResponseCategoriesDto> findByNameParams(Pageable pageable, String param);

    Categories save(RequestCategoriesDto request);

    Categories update(Long categoryId, RequestCategoriesDto categoryData);

    void deleteById(Long categoryId);

}