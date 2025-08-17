package br.com.catalog.exceptions;

import java.util.List;

public class BusinessRuleException extends RuntimeException {
    private final List<ErrorMessageDTO> errors;

    public BusinessRuleException(String message, String field) {
        super(message);
        this.errors = List.of(new ErrorMessageDTO(message, field));
    }

    public List<ErrorMessageDTO> getErrors() {
        return errors;
    }
}
