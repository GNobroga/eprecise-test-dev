package br.com.eprecise.domain.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ReflectionUtilsTest {
   
    static class BaseClass {
        private String baseField1;
        protected Integer baseField2;
    }

    static class SubClass extends BaseClass {
        private String subField1;
        public Double subField2;
    }

    static class EmptyClass {}

    static class Duplicate2 {
        private String fieldDuplicated;
    }

    static class Duplicate1 extends Duplicate2 {
        private String fieldDuplicated;
    }

    @Test
    void testGetFieldNamesNoInheritance() {
        List<String> fieldNames = ReflectionUtils.getFieldNames(EmptyClass.class);
        assertTrue(fieldNames.isEmpty());
    }

    @Test
    void testGetFieldNamesWithInheritance() {
        List<String> fieldNames = ReflectionUtils.getFieldNames(SubClass.class);
        List<String> expectedFieldNames = Arrays.asList("subField1", "subField2", "baseField1", "baseField2");
        assertEquals(expectedFieldNames, fieldNames);
    }

    @Test
    void testGetFieldNamesDuplicated() {
        List<String> fieldNames = ReflectionUtils.getFieldNames(Duplicate2.class);
        List<String> expectedFieldNames = Arrays.asList("fieldDuplicated", "fieldDuplicated");
        assertEquals(expectedFieldNames, fieldNames);
    }

}
