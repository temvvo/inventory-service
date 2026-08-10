package com.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

/*
 * Supports Pagination
 * Should be mapped to Page in DB layer
 * Designed to keep spring data separated from Business layer
 * and separated from DB layer(Spring data)
 */
public class PagedProduct<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
