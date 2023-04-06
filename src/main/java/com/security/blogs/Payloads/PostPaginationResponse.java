package com.security.blogs.Payloads;

import lombok.Data;

import java.util.List;


// This class is used to get the response of Pagination in the API
@Data
public class PostPaginationResponse {

    private List<PostDto> content;

    private int pageNumber;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean isLast;

}
