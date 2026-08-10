package com.challenge.adapter.api.model;

import lombok.Data;
import java.util.List;

/*
 * Supports Pagination
 * Should be mapped to PagedProduct in application layer
 * Designed to keep spring data separated from web layer
 */
@Data
public class PagedResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
