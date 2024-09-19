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
    void testGetFieldNamesWithDuplicates() {
        class A {
            private String duplicateField;
        }

        class B extends A {
            private String duplicateField;
        }

        List<String> fieldNames = ReflectionUtils.getFieldNames(B.class);
        List<String> expectedFieldNames = Arrays.asList("duplicateField"); 
        assertEquals(expectedFieldNames, fieldNames, "The field names list should not contain duplicates.");
    }

    @Test
    void testGetFieldNamesWithDifferentVisibility() {

        class VisibilityClass {
            public String publicField;
            protected String protectedField;
            private String privateField;
        }

        List<String> fieldNames = ReflectionUtils.getFieldNames(VisibilityClass.class);
        List<String> expectedFieldNames = Arrays.asList("publicField", "protectedField", "privateField");
        assertEquals(expectedFieldNames, fieldNames, "The field names list should include all fields regardless of their visibility.");
    }
}
