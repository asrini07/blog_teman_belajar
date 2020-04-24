package com.example.temanbelajar.service.impl;

import com.example.temanbelajar.config.DateTime;
import com.example.temanbelajar.dto.request.AuthorRequestDto;
import com.example.temanbelajar.dto.request.AuthorRequestPassDto;
import com.example.temanbelajar.dto.request.AuthorRequestUpdateDto;
import com.example.temanbelajar.dto.response.AuthorResponseDto;
import com.example.temanbelajar.dto.response.AuthorResponseUpdateDto;
import com.example.temanbelajar.dto.response.PasswordResponseUpdateDto;
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
    public Page<AuthorResponseDto> findAll(Pageable pageable) {

        try {

            return authorRepository.findAll(pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }

    }

    @Override
    public AuthorResponseDto findById(Long id) {

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
    public Page<AuthorResponseDto> findByNameParams(Pageable pageable, String param) {

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
    public Author save(AuthorRequestDto request) {

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
    public AuthorResponseUpdateDto update(Long id, AuthorRequestUpdateDto request) {

        try {

            Author author = authorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, FIELD, id.toString()));

            BeanUtils.copyProperties(request, author);
            author.setUpdated_at(dateTime.getCurrentDate());
            authorRepository.save(author);
            return fromEntityUpdateAuthor(author);
            //return author;

        } catch (ResourceNotFoundException e) {

            log.error(e.getMessage(), e);
            throw e;

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    @Override
    public PasswordResponseUpdateDto changePassword(Long authorId, AuthorRequestPassDto authorData) {
        try {

            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, FIELD, authorId.toString()));

            BeanUtils.copyProperties(authorData, author);
            author.setPassword(passwordEncoder().encode(author.getPassword()));
            author.setUpdated_at(dateTime.getCurrentDate());
            authorRepository.save(author);
            return fromEntityChangePassword(author);
            //return author;

        } catch (ResourceNotFoundException e) {

            log.error(e.getMessage(), e);
            throw e;

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;

        }
    }

    private AuthorResponseDto fromEntity(Author author) {

        AuthorResponseDto response = new AuthorResponseDto();
        BeanUtils.copyProperties(author, response);
        return response;

    }

    private PasswordResponseUpdateDto fromEntityChangePassword(Author author) {

        PasswordResponseUpdateDto response = new PasswordResponseUpdateDto();
        BeanUtils.copyProperties(author, response);
        return response;

    }

    private AuthorResponseUpdateDto fromEntityUpdateAuthor(Author author) {

        AuthorResponseUpdateDto response = new AuthorResponseUpdateDto();
        BeanUtils.copyProperties(author, response);
        return response;

    }

    


}