package com.example.temanbelajar.dto.request;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUpdateAuthorDto {

    @Column(length = 45, nullable = false)
    @Size(min = 3, max = 45)
    private String first_name;

    @Column(length = 45)
    @Size(min = 3, max = 45)
    private String last_name;

    @Column(length = 45, unique = true, nullable = false)
    @Size(min = 3, max = 45)
    private String username;

}