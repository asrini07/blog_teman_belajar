package com.example.temanbelajar.service;

import com.example.temanbelajar.model.Categories;
import com.example.temanbelajar.repository.CategoriesRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * CategoriesService
 */
@Service
@Slf4j
public class CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    public Categories update(Long id, Categories categories) {
        categories.setId(id);

        return categoriesRepository.save(categories);
    }

    public Page<Categories> findAll(Pageable pageable) {
        try {

            return categoriesRepository.findAll(pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public Page<Categories> findByNameParams(Pageable pageable, String param) {

        try {
            param = param.toLowerCase();
            return categoriesRepository.findByNameParams(pageable, param).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private Categories fromEntity(Categories categories) {
        Categories response = new Categories();
        BeanUtils.copyProperties(categories, response);
        return response;
    }

}