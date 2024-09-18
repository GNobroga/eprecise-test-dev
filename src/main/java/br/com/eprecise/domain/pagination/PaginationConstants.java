package br.com.eprecise.domain.pagination;

public class PaginationConstants {
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final OrderType DEFAULT_PAGE_ORDER = OrderType.ASC;
    
    public static final String PARAM_PAGE_SIZE = "pageSize";
    public static final String PARAM_PAGE_NUMBER = "pageNumber";
    public static final String PARAM_PAGE_ORDER = "pageOrder";
}
