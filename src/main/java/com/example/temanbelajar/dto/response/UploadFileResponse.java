package com.example.temanbelajar.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileResponse {

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
    private String name;

    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size, String name) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
        this.name = name;
    }

    // public String getFileName() {
    //     return fileName;
    // }

    // public void setFileName(String fileName) {
    //     this.fileName = fileName;
    // }

    // public String getFileDownloadUri() {
    //     return fileDownloadUri;
    // }

    // public void setFileDownloadUri(String fileDownloadUri) {
    //     this.fileDownloadUri = fileDownloadUri;
    // }

    // public String getFileType() {
    //     return fileType;
    // }

    // public void setFileType(String fileType) {
    //     this.fileType = fileType;
    // }

    // public long getSize() {
    //     return size;
    // }

    // public void setSize(long size) {
    //     this.size = size;
    // }

}