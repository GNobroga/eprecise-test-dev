package br.com.eprecise.domain.pagination;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Page<T> {    
    private Integer pageNumber;
    private Integer pageSize;
    private String pageOrder;
    private Long totalPages;
    private List<T> items;

    public static <T> Page<T> create(Pagination pagination, List<T> items, long totalItems) {
        return Page.<T>builder()
            .pageNumber(pagination.getPageNumber())
            .pageSize(pagination.getPageSize())
            .pageOrder(pagination.getPageOrder().getValue())
            .totalPages((long) Math.floor((double) totalItems / pagination.getPageSize()))
            .items(items) 
            .build();
    }
}
