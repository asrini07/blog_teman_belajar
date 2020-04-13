package com.example.temanbelajar.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * ResponseUpdateAuthorDto
 */
@Getter
@Setter
public class ResponseUpdateAuthorDto {

    private Long id;
    private String first_name;
    private String last_name;
    private String username;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone="GMT+7")
    private Date created_at;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone="GMT+7")
    private Date updated_at; 
    
}