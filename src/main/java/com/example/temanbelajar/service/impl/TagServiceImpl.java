package com.example.temanbelajar.service.impl;

import com.example.temanbelajar.config.DateTime;
import com.example.temanbelajar.dto.request.RequestTagDto;
import com.example.temanbelajar.dto.response.ResponseTagDto;
import com.example.temanbelajar.exeption.ResourceNotFoundException;
import com.example.temanbelajar.model.Tags;
import com.example.temanbelajar.repository.TagRepository;
import com.example.temanbelajar.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;


@Slf4j
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Tag";
    private static final String FIELD = "id";

    @Override
    public Page<ResponseTagDto> findAll(Pageable pageable) {

        try {

            return tagRepository.findAll(pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }

    }

    @Override
    public ResponseTagDto findById(Long id) {

        try {

            Tags tags = tagRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));
            
            return fromEntity(tags);

        } catch (ResourceNotFoundException e) {

            log.error(e.getMessage(), e);
            throw e;

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Page<ResponseTagDto> search(Pageable pageable, String param) {

        try {

            param = param.toLowerCase();
            return tagRepository.search(pageable, param).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    // @Override
    // public ResponseTagDto findByName(String name) {
    //     try {
    //         Tags tag = tagRepository.findByName(name);

    //         return fromEntity(tag);
    //     } catch (Exception e) {
    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }

    @Override
    public void deleteById(Long id) {

        try {

            Tags tags = tagRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));

            tagRepository.deleteById(id);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Tags save(RequestTagDto request) {

        try {

            Tags tags = new Tags();
            BeanUtils.copyProperties(request, tags);
         
            return tagRepository.save(tags);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Tags update(Long id, RequestTagDto request) {

        try {

            Tags tags = tagRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));
            
            BeanUtils.copyProperties(request, tags);
            tags.setUpdated_at(dateTime.getCurrentDate());
            tagRepository.save(tags);
            return tags;

        } catch (ResourceNotFoundException e) {

            log.error(e.getMessage(), e);
            throw e;

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    private ResponseTagDto fromEntity(Tags tags) {

        ResponseTagDto response = new ResponseTagDto();
        BeanUtils.copyProperties(tags, response);
        return response;
        
    }

}