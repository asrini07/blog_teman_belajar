package com.example.temanbelajar.service;

import com.example.temanbelajar.model.Tags;
import com.example.temanbelajar.repository.TagRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * TagService
 */
@Slf4j
@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public Tags update(Long id, Tags tags) {
       
        tags.setId(id);

        return tagRepository.save(tags);

    }

    public Tags saveTag(Tags tags, String tag_name) {
        tags.setName(tag_name);
        
        return tagRepository.save(tags);
    }

    public Page<Tags> findAll(Pageable pageable) {
        try {

            return tagRepository.findAll(pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public Page<Tags> findByNameParams(Pageable pageable, String param) {

        try {
            param = param.toLowerCase();
            return tagRepository.findByNameParams(pageable, param).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private Tags fromEntity(Tags tags) {
        Tags response = new Tags();
        BeanUtils.copyProperties(tags, response);
        return response;
    }

    
}