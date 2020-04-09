package com.example.temanbelajar.dto.request;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RequestTagDto {

    @Column(length = 20, nullable = false, unique = true)
    @Size(min = 2, max = 20)
    @NotBlank
    private String name;

}