package com.example.temanbelajar.service.impl;

import com.example.temanbelajar.config.DateTime;
import com.example.temanbelajar.dto.request.RequestAuthorDto;
import com.example.temanbelajar.dto.request.RequestAuthorPassDto;
import com.example.temanbelajar.dto.request.RequestUpdateAuthorDto;
import com.example.temanbelajar.dto.response.ResponseAuthorDto;
import com.example.temanbelajar.exeption.ResourceNotFoundException;
import com.example.temanbelajar.model.Author;
import com.example.temanbelajar.repository.AuthorRepository;
import com.example.temanbelajar.service.AuthorService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private DateTime dateTime;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private static final String RESOURCE = "Author";
    private static final String FIELD = "id";

    @Override
    public Page<ResponseAuthorDto> findAll(Pageable pageable) {

        try {

            return authorRepository.findAll(pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }

    }

    @Override
    public ResponseAuthorDto findById(Long id) {

        try {

            Author author = authorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));

            return fromEntity(author);

        } catch (ResourceNotFoundException e) {

            log.error(e.getMessage(), e);
            throw e;

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Page<ResponseAuthorDto> findByNameParams(Pageable pageable, String param) {

        try {

            param = param.toLowerCase();
            return authorRepository.findByNameParams(pageable, param).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public void deleteById(Long id) {

        try {

            Author author = authorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));

            authorRepository.deleteById(id);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Author save(RequestAuthorDto request) {

        try {

            Author author = new Author();
            BeanUtils.copyProperties(request, author);

            author.setPassword(passwordEncoder().encode(author.getPassword()));
            return authorRepository.save(author);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Author update(Long id, RequestUpdateAuthorDto request) {

        try {

            Author author = authorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));

            BeanUtils.copyProperties(request, author);
            author.setUpdated_at(dateTime.getCurrentDate());
            authorRepository.save(author);
            return author;

        } catch (ResourceNotFoundException e) {

            log.error(e.getMessage(), e);
            throw e;

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public Author changePassword(Long authorId, RequestAuthorPassDto authorData) {
        try {

            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, FIELD, authorId.toString()));

            BeanUtils.copyProperties(authorData, author);
            author.setPassword(passwordEncoder().encode(author.getPassword()));
            author.setUpdated_at(dateTime.getCurrentDate());
            authorRepository.save(author);
            return author;

        } catch (ResourceNotFoundException e) {

            log.error(e.getMessage(), e);
            throw e;

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    private ResponseAuthorDto fromEntity(Author author) {

        ResponseAuthorDto response = new ResponseAuthorDto();
        BeanUtils.copyProperties(author, response);
        return response;

    }

    


}