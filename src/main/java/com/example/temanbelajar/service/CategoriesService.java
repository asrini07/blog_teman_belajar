package com.example.temanbelajar.service;

import com.example.temanbelajar.dto.request.CategoriesRequestDto;
import com.example.temanbelajar.dto.response.CategoriesResponseDto;
import com.example.temanbelajar.model.Categories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * TagService
 */
public interface CategoriesService {

    Page<CategoriesResponseDto> findAll(Pageable pageable);

    CategoriesResponseDto findById(Long categoryId);

    Page<CategoriesResponseDto> findByNameParams(Pageable pageable, String param);

    Categories save(CategoriesRequestDto request);

    Categories update(Long categoryId, CategoriesRequestDto categoryData);

    void deleteById(Long categoryId);

}