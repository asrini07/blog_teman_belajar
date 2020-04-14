package com.example.temanbelajar.dto;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ResponsePagination
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ResponseBaseDto<Any> {
    
    private boolean status;
    private Integer code;
    private String message;
    private Any data;

    public static ResponseBaseDto error(Integer code, String message) {
        return new ResponseBaseDto<>(false, code, message, null);
    }

    public static ResponseBaseDto ok() {
        return new ResponseBaseDto<>(true, 200, "Success", null);
    }

    public static <I> ResponseBaseDto<I> ok(I body) {
        return new ResponseBaseDto<I>(true, 200, "Success", body);
    }

    public static ResponseBaseDto created() {
        return new ResponseBaseDto<>(true, 201, "Created", null);
    }

    public static <I>ResponseBaseDto<I> saved(I body) {
        return new ResponseBaseDto<I>(true, 201, "Created", body);
    }

    public static ResponseBaseDto created(String uri) {
        ResponseBaseDto<Map> baseResponse = new ResponseBaseDto<>();
        baseResponse.setStatus(true);
        baseResponse.setCode(201);
        baseResponse.setMessage("Created");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("uri", uri);
        baseResponse.setData(map);
        return baseResponse;
    }

    
}