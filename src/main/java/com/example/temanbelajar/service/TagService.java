package com.example.temanbelajar.service;

import com.example.temanbelajar.model.Tags;
import com.example.temanbelajar.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TagService
 */
@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public Tags update(Long id, Tags tags) {
       
        tags.setId(id);

        return tagRepository.save(tags);

    }

    
}