package com.example.temanbelajar.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadResponseDto {

    private Long id;
    private String title;
   // private String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/get/")
            //    .path(file.getOriginalFilename()).toUriString();
    private String imageUrl;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone="GMT+7")
    private Date created_at;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone="GMT+7")
    private Date updated_at;

}