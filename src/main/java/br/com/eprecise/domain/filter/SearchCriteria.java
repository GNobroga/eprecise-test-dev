package br.com.eprecise.domain.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import br.com.eprecise.domain.pagination.Pagination;
import lombok.Data;

@Data 
public class SearchCriteria {
    
    private List<AttributeFilterEqual> equalFilters;
    
    private Pagination pagination;

    private static final String LIKE_FILTER_KEY = "like_filters";
    private static final String LIKE_FILTER_DELIMITER = ",";
    private static final String KEY_VALUE_DELIMITER = "=";

    public SearchCriteria(Map<String, String> params) {
        if (Objects.isNull(params)) {
            params = new HashMap<>();
        }
        pagination = Pagination.create(params);
        extractEqualFilters(params);
    }

    private void extractEqualFilters(final Map<String, String> params) {
        if (!params.containsKey(LIKE_FILTER_KEY)) {
            this.equalFilters = new ArrayList<>();
            return;
        }
        String equalFiltersStr = params.get(LIKE_FILTER_KEY);
        
        // equal_filters=name=Gabriel,name=Pedro

        equalFilters = new ArrayList<>();

        for (String equalFilterParsed: equalFiltersStr.split(LIKE_FILTER_DELIMITER)) {
            String[] equalFilter = equalFilterParsed.split(KEY_VALUE_DELIMITER );

            if (equalFilter.length != 2) {
                continue;
            }

            String fieldName = equalFilter[0];
            String value = equalFilter[1];

            if (!fieldName.trim().isEmpty() && !value.trim().isEmpty()) {
                equalFilters.add(new AttributeFilterEqual(fieldName, value));
            }
        }
    }
}
