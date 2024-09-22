package br.com.eprecise.domain.filter;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class AttributeFilter {
    private final String fieldName;
    private final String value;
}
