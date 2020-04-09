package com.example.temanbelajar.dto.request;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RequestCategoriesDto {

    @Column(length = 45, nullable = false)
    @NotBlank(message = "Name Category must not be empty")
    @Size(min = 3, max = 45, message = "Name Category min 3 and max 45 character")
    private String name;

}