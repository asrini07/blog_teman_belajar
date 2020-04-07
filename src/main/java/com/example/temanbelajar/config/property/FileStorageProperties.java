package com.example.temanbelajar.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "file")
@Getter
@Setter
public class FileStorageProperties {

    private String uploadDir;

    // public String getUploadDir() {
    //     return uploadDir;
    // }

    // public void setUploadDir(String uploadDir) {
    //     this.uploadDir = uploadDir;
    // }

}