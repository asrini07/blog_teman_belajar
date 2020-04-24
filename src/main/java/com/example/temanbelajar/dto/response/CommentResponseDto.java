package com.example.temanbelajar.dto.response;

import java.util.Date;

import com.example.temanbelajar.model.Blog;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {

    private Long id;
    private BlogResponseDto blog;
    //private transient Long blog_id;
    private String guest_email;
    private String content;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone="GMT+7")
    private Date created_at;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone="GMT+7")
    private Date updated_at; 


}