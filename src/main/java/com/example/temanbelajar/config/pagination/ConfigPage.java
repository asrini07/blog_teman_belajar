package com.example.temanbelajar.config.pagination;

import java.util.List;

import lombok.Data;

/**
 * ConfigPage
 */
@Data
public class ConfigPage<T> {
    
    private Integer currentPage;
    private Long total;
    private Integer perPage;
    private Integer lastPage;
    private String nextPageUrl;
    private String prevPageUrl;
    private Long from;
    private Long to;
    private List<T> data;
    
}