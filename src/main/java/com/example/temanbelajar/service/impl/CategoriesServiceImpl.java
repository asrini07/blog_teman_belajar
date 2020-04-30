package com.example.temanbelajar.service.impl;

import com.example.temanbelajar.config.DateTime;
import com.example.temanbelajar.dto.request.CategoriesRequestDto;
import com.example.temanbelajar.dto.response.CategoriesResponseDto;
import com.example.temanbelajar.exeption.ResourceNotFoundException;
import com.example.temanbelajar.model.Categories;
import com.example.temanbelajar.repository.CategoriesRepository;
import com.example.temanbelajar.service.CategoriesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;


@Slf4j
@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Category";
    private static final String FIELD = "id";

    @Override
    public Page<CategoriesResponseDto> findAll(Pageable pageable) {

        try {

            return categoriesRepository.findAll(pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }

    }

    @Override
    public CategoriesResponseDto findById(Long id) {

        try {

            Categories categories = categoriesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));
            
            return fromEntity(categories);

        } catch (ResourceNotFoundException e) {

            log.error(e.getMessage(), e);
            throw e;

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Page<CategoriesResponseDto> findByNameParams(Pageable pageable, String param) {

        try {

            param = param.toLowerCase();
            return categoriesRepository.findByNameParams(pageable, param).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public void deleteById(Long id) {

        try {

            Categories categories = categoriesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));

            categoriesRepository.deleteById(id);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Categories save(CategoriesRequestDto request) {

        try {

            Categories categories = new Categories();
            BeanUtils.copyProperties(request, categories);
         
            return categoriesRepository.save(categories);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Categories update(Long id, CategoriesRequestDto request) {

        try {

            Categories categories = categoriesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));
            
            BeanUtils.copyProperties(request, categories);
            categories.setUpdated_at(dateTime.getCurrentDate());
            categoriesRepository.save(categories);
            return categories;

        } catch (ResourceNotFoundException e) {

            log.error(e.getMessage(), e);
            throw e;

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    private CategoriesResponseDto fromEntity(Categories categories) {

        CategoriesResponseDto response = new CategoriesResponseDto();
        BeanUtils.copyProperties(categories, response);
        return response;
        
    }


}