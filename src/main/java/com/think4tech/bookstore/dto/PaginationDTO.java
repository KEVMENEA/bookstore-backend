package com.think4tech.bookstore.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PaginationDTO {
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
}
