package com.example.temanbelajar.config.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Data;

/**
 * ConfigPageable
 */
@Data
public class ConfigPageable {

    private Integer page;
    private Integer size;
    private String sort;
    private String sortBy;

    public static Pageable convertToPageable(ConfigPageable configPageable) {

        if (configPageable != null) {
            
            int page;
            if (configPageable.getPage() != null) {
                if (configPageable.getPage() == 0) {
                    page = 0;
                } else {
                    page = configPageable.getPage() - 1;
                }
            } else {
                page = 0;
            }

            int size;
            if (configPageable.getSize() != null) {
                size = configPageable.getSize();
            } else {
                size = 20;
            }

            if(configPageable.getSortBy() != null) {
                if(configPageable.getSort().toLowerCase().equals("asc")){
                    return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, configPageable.getSortBy()));
                }

                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, configPageable.getSortBy() ));
            }

            return PageRequest.of(page, size);

        } else {

            if(configPageable.getSortBy() != null) {
                if(configPageable.getSort().toLowerCase().equals("asc")){
                    return PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, configPageable.getSortBy()));
                }

                return PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, configPageable.getSortBy() ));
            }
            return PageRequest.of(0, 20);

        }
        
    }


}