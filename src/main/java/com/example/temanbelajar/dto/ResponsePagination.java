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
public class ResponsePagination<Any> {
    
    private boolean status;
    private Integer code;
    private String message;
    private Any data;

    public static ResponsePagination error(Integer code, String message) {
        return new ResponsePagination<>(false, code, message, null);
    }

    public static ResponsePagination ok() {
        return new ResponsePagination<>(true, 200, "Success", null);
    }

    public static <I> ResponsePagination<I> ok(I body) {
        return new ResponsePagination<I>(true, 200, "Success", body);
    }

    public static ResponsePagination created() {
        return new ResponsePagination<>(true, 201, "Created", null);
    }

    public static ResponsePagination created(String uri) {
        ResponsePagination<Map> baseResponse = new ResponsePagination<>();
        baseResponse.setStatus(true);
        baseResponse.setCode(201);
        baseResponse.setMessage("Created");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("uri", uri);
        baseResponse.setData(map);
        return baseResponse;
    }

    
}