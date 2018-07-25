package ru.vood.admplugin.infrastructure.spring.except;

public class ApplicationException extends RuntimeException {

    protected ApplicationException() {
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(String message) {
        super(message);
    }
}
