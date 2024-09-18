package br.com.eprecise.domain.pagination;

import java.util.Map;
import java.util.Objects;

import lombok.Getter;

@Getter
public class Pagination {

    private Integer pageSize;
    private Integer pageNumber;
    private OrderType pageOrder;

    private Pagination(Map<String, String> params) {
        params = Objects.requireNonNull(params);
        extractPageSize(params);
        extractPageNumber(params);
        extractPageOrder(params);
    }

    private void extractPageSize(final Map<String, String> params) {
        if (!params.containsKey(PaginationConstants.PARAM_PAGE_SIZE)) {
            this.pageSize = PaginationConstants.DEFAULT_PAGE_SIZE;
            return;
        }

        String pageSizeStr = params.get(PaginationConstants.PARAM_PAGE_SIZE);

        if (Objects.nonNull(pageSizeStr) && pageSizeStr.matches("\\d+")) {
            int parsedPageSize = Integer.parseInt(pageSizeStr);
            if (parsedPageSize > 0 && parsedPageSize <= PaginationConstants.MAX_PAGE_SIZE) {
                this.pageSize = parsedPageSize;
            } else {
                this.pageSize = PaginationConstants.DEFAULT_PAGE_SIZE;
            }
        } else {
            this.pageSize = PaginationConstants.DEFAULT_PAGE_SIZE;
        }
    }

    private void extractPageNumber(final Map<String, String> params) {
        if (!params.containsKey(PaginationConstants.PARAM_PAGE_NUMBER)) {
            this.pageNumber = PaginationConstants.DEFAULT_PAGE_NUMBER;
            return;
        }

        String pageNumberStr = params.get(PaginationConstants.PARAM_PAGE_NUMBER);

        if (Objects.nonNull(pageNumberStr) && pageNumberStr.matches("\\d+")) {
            this.pageNumber = Math.max(Integer.parseInt(pageNumberStr), PaginationConstants.DEFAULT_PAGE_NUMBER);
        } else {
            this.pageNumber = PaginationConstants.DEFAULT_PAGE_NUMBER;
        }
    }

    private void extractPageOrder(final Map<String, String> params) {
        if (!params.containsKey(PaginationConstants.PARAM_PAGE_ORDER)) {
            this.pageOrder = OrderType.ASC;
            return;
        }

        final String pageOrder = params.get(PaginationConstants.PARAM_PAGE_ORDER);

        if (Objects.nonNull(pageOrder) && OrderType.ASC.equalValue(pageOrder) || OrderType.DESC.equalValue(pageOrder)) {
            this.pageOrder = OrderType.fromValue(pageOrder);
        } else {
            this.pageOrder = PaginationConstants.DEFAULT_PAGE_ORDER;
        }
    }

    public static Pagination create(final Map<String, String> params) {
        return new Pagination(params);
    }
}
