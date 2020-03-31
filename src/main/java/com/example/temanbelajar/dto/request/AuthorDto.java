package com.example.temanbelajar.dto.request;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * AuthoeDto
 */
@Getter
@Setter
public class AuthorDto {

    @Column(length = 45, nullable = false)
    @Size(min = 3, max = 45)
    private String nama_depan;

    @Column(length = 45)
    @Size(min = 3, max = 45)
    private String nama_belakang;

    @Column(length = 45, unique = true, nullable = false)
    @Size(min = 3, max = 45)
    private String username;
    
}