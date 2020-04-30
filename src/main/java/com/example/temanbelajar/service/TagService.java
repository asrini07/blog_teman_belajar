package com.example.temanbelajar.service;

import com.example.temanbelajar.dto.request.TagRequestDto;
import com.example.temanbelajar.dto.response.TagResponseDto;
import com.example.temanbelajar.model.Tags;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * TagService
 */
public interface TagService {

    Page<TagResponseDto> findAll(Pageable pageable);

    TagResponseDto findById(Long tagId);

    Page<TagResponseDto> search(Pageable pageable, String param);

    Tags save(TagRequestDto request);

    Tags update(Long tagId, TagRequestDto tagData);

    void deleteById(Long tagId);

}