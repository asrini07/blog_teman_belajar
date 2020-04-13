package com.example.temanbelajar.dto.request;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * BlogDto
 */
@Getter
@Setter
public class RequestUpdateBlogDto {

    @Column(length = 150, nullable = false)
    @Size(min = 3, max = 150, message = "Lastname min 3 and max 150 character")
    @NotBlank
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(min = 10, message = "Content min 10 character")
    @NotBlank
    private String content;

    //private List<Tags> tag = new ArrayList<>();
    
}