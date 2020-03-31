package com.example.temanbelajar.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * ResponseBaseDto
 */
@Getter
@Setter
public class ResponseError<Any> {

    private Boolean status = false;
    private Integer code = 500;
    private String message = "Internal Server Error";
    //private Any data;

    @Override
    public String toString() {
        
        return "{\"status\": " + status + ", \"code\": " + code + ", \"message\": \"" + message  + "\"}";
    
    }


}