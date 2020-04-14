package com.example.temanbelajar.service;

import com.example.temanbelajar.model.Tags;
import com.example.temanbelajar.dto.request.RequestTagDto;
import com.example.temanbelajar.dto.response.ResponseTagDto;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * TagService
 */
public interface TagService {

    Page<ResponseTagDto> findAll(Pageable pageable);

    ResponseTagDto findById(Long tagId);

    Page<ResponseTagDto> search(Pageable pageable, String param);

    Tags save(RequestTagDto request);

    Tags update(Long tagId, RequestTagDto tagData);

    void deleteById(Long tagId);

}