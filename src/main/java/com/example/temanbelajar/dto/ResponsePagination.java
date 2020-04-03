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
public class ResponsePagination<T> {
    
    private boolean status;
    private String code;
    private String message;
    private T data;

    public static ResponsePagination error(String code, String message) {
        return new ResponsePagination<>(false, code, message, null);
    }

    public static ResponsePagination ok() {
        return new ResponsePagination<>(true, "200", "success", null);
    }

    public static <I> ResponsePagination<I> ok(I body) {
        return new ResponsePagination<I>(true, "200", "success", body);
    }

    public static ResponsePagination created() {
        return new ResponsePagination<>(true, "201", "created", null);
    }

    public static ResponsePagination created(String uri) {
        ResponsePagination<Map> baseResponse = new ResponsePagination<>();
        baseResponse.setStatus(true);
        baseResponse.setCode("201");
        baseResponse.setMessage("created");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("uri", uri);
        baseResponse.setData(map);
        return baseResponse;
    }

    
}