package br.com.eprecise.domain.exceptions;

public abstract class NoStackTraceException extends RuntimeException {
    public NoStackTraceException(String message) {
        super(message, null, true, false);
    }
}
