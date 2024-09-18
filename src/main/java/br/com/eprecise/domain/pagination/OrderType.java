package br.com.eprecise.domain.pagination;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderType {
    ASC("asc"),
    DESC("desc");

    private final String value;

    public static OrderType fromValue(String value) {
        for (final OrderType orderType: OrderType.values()) {
            if (orderType.equalValue(value)) {
                return orderType;
            }
        }
        return null;
    }

    public boolean equalValue(String value) {
        value = value.toLowerCase();
        return getValue().equals(value);
    }
}
