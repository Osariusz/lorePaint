package org.osariusz.lorepaint.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.Set;

public class Validation {

    public static <T> void validate(T object, Validator validator) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                builder.append(violation.getMessage());
                builder.append("\n");
            }
            throw new ConstraintViolationException("Violated " + object.getClass() + " constraints: " + builder, violations);
        }
    }
}
