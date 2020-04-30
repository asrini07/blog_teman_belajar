package com.example.temanbelajar.dto.request;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

/**
 * AuthorPassDto
 */
@Getter
@Setter
public class AuthorRequestPassDto {

    @Column(length = 150, nullable = false)
    private String password;
    
}