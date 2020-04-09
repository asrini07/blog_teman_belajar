package com.example.temanbelajar.dto;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// import lombok.AllArgsConstructor;
// import lombok.Data;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter; 

/**
 * ResponseBaseDto
 */
@Getter
@Setter
// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ResponseBaseDtoLama<Any> {

    private Boolean status = true;
    private Integer code = 200;
    private String message = "Success";
    private Any data;

    @Override
    public String toString() {
        
        return "{\"status\": " + status + ", \"code\": " + code + ", \"message\": \"" + message + "\", \"data\": " + data + "}";
    
    }


}