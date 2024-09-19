package br.com.eprecise.unit.domain.pagination;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.eprecise.domain.pagination.OrderType;
import br.com.eprecise.domain.pagination.Pagination;

public class PaginationTest {

    @Test
    void testValidPaginationParams() {
        final Integer expectedPageSize = 10;
        final Integer expectedPageNumber = 2;
        final OrderType expectedPageOrder = OrderType.DESC;

        final Map<String, String> params = new HashMap<>();
        params.put("pageSize", "10");
        params.put("pageNumber", "2");
        params.put("pageOrder", "DESC");

        final Pagination pagination = Pagination.create(params);

        Assertions.assertEquals(expectedPageSize, pagination.getPageSize());
        Assertions.assertEquals(expectedPageNumber, pagination.getPageNumber());
        Assertions.assertEquals(expectedPageOrder, pagination.getPageOrder());
    }

    @Test
    void testInvalidPageSize() {
        final Integer expectedPageSize = 20;
        final Map<String, String> params = new HashMap<>();
        params.put("pageSize", "invalid");
        params.put("pageNumber", "2");
        params.put("pageOrder", "DESC");

        final Pagination pagination = Pagination.create(params);
        Assertions.assertEquals(expectedPageSize, pagination.getPageSize());
    }

    @Test
    void testInvalidPageNumber() {
        final Integer expectedPageNumber = 1;
        final Map<String, String> params = new HashMap<>();
        params.put("pageSize", "10");
        params.put("pageNumber", "invalid");
        params.put("pageOrder", "DESC");

        final Pagination pagination = Pagination.create(params);
        Assertions.assertEquals(expectedPageNumber, pagination.getPageNumber());
    }

    @Test
    void testInvalidPageOrder() {
        final Map<String, String> params = new HashMap<>();
        params.put("pageSize", "10");
        params.put("pageNumber", "2");
        params.put("pageOrder", "INVALID");
        final Pagination pagination = Pagination.create(params);
        Assertions.assertEquals(OrderType.ASC, pagination.getPageOrder(), "Invalid pageOrder should default to ASC");
    }

    @Test
    void testDefaultValues() {
        final Pagination pagination = Pagination.create(new HashMap<>());
        Assertions.assertEquals(20, pagination.getPageSize(), "Default pageSize should be 20");
        Assertions.assertEquals(1, pagination.getPageNumber(), "Default pageNumber should be 1");
        Assertions.assertEquals(OrderType.ASC, pagination.getPageOrder(), "Default pageOrder should be ASC");
    }

}
