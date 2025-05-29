package com.sys4business.sys4mech.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(String message, Long timestamp, Integer status) {
        super(message, timestamp, status);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addErrors(String fieldName, String message) {
        this.errors.add(new FieldMessage(fieldName, message));
    }
    
}
