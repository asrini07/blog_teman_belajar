package com.example.temanbelajar.dto.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.temanbelajar.model.Author;
import com.example.temanbelajar.model.Categories;
import com.example.temanbelajar.model.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogResponseDto {

    private Long id;
    private String title;
    private String content;
    private Categories categories;
    private Author author;
    private List<TagResponseDto> tag = new ArrayList<>();
    private Set<Comment> comments = new HashSet<>();
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone="GMT+7")
    private Date created_at;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone="GMT+7")
    private Date updated_at; 

}