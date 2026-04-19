package com.think4tech.bookstore.service.util;

import com.think4tech.bookstore.dto.PageDTO;
import com.think4tech.bookstore.dto.PaginationDTO;
import org.springframework.data.domain.Page;

public class PageUtil {
    private PageUtil() {

    }

    public static <T> PageDTO<T> toPageDTO(Page<T> page) {
        PaginationDTO pagination = PaginationDTO.builder()
                .pageNumber(page.getNumber() + 1) // // convert 0-based to 1-based
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();

        return PageDTO.<T>builder()
                .content(page.getContent())
                .pagination(pagination)
                .build();
    }
}
