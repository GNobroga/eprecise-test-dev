package br.com.eprecise.domain.exceptions;

public class AttributeNotFoundException extends NoStackTraceException {
    public AttributeNotFoundException(String fieldName) {
        super(String.format("Attribute '%s' not found in the entity.", fieldName));
    }
}