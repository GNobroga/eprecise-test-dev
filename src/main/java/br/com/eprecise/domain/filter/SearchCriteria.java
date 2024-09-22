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
    
    private List<AttributeFilter> attributes;
    
    private Pagination pagination;

    public static final String FILTER_KEY = "like_filters";
    private static final String FILTER_DELIMITER = ",";
    private static final String KEY_VALUE_DELIMITER = "=";

    public SearchCriteria(Map<String, String> params) {
        if (Objects.isNull(params)) {
            params = new HashMap<>();
        }
        pagination = Pagination.create(params);
        extractAttributes(params);
    }

    private void extractAttributes(final Map<String, String> params) {
        if (!params.containsKey(FILTER_KEY)) {
            this.attributes = new ArrayList<>();
            return;
        }
        String attributesStr = params.get(FILTER_KEY);

        attributes = new ArrayList<>();

        for (String equalFilterParsed: attributesStr.split(FILTER_DELIMITER)) {
            String[] equalFilter = equalFilterParsed.split(KEY_VALUE_DELIMITER );

            if (equalFilter.length != 2) {
                continue;
            }

            String fieldName = equalFilter[0];
            String value = equalFilter[1];

            if (!fieldName.trim().isEmpty() && !value.trim().isEmpty()) {
                attributes.add(new AttributeFilter(fieldName, value));
            }
        }
    }
}
