package com.example.temanbelajar.dto.request;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    @Column(length = 80)
    @Size(min = 10, max = 80)
    @Email(message = "email not valid")
    private String guest_email;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Content can not empty")
    private String content;

    private transient Long blog_id;


}