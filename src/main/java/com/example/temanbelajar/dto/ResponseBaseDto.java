package com.example.temanbelajar.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * ResponseBaseDto
 */
@Getter
@Setter
public class ResponseBaseDto<Any> {

    private Boolean status = true;
    private Integer code = 200;
    private String message = "Success";
    private Any data;

    @Override
    public String toString() {
        
        return "{\"status\": " + status + ", \"code\": " + code + ", \"message\": \"" + message + "\", \"data\": " + data + "}";
    
    }


}