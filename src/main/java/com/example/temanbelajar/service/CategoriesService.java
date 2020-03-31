package com.example.temanbelajar.service;

import com.example.temanbelajar.model.Categories;
import com.example.temanbelajar.repository.CategoriesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CategoriesService
 */
@Service
public class CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    public Categories update(Long id, Categories categories) {
        categories.setId(id);

        return categoriesRepository.save(categories);
    }
}