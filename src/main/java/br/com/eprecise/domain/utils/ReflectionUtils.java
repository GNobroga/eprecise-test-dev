package br.com.eprecise.domain.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ReflectionUtils {

    private ReflectionUtils() {}
        
    public static List<String> getFieldNames(Class<?> clazz) {
        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));

        Class<?> superClass = clazz.getSuperclass();
        while (Objects.nonNull(superClass)) {
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        }

        return fields.stream()
            .map(Field::getName)
            .distinct() 
            .collect(Collectors.toList());
    }
}
