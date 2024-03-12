package com.pumpkinmarket.validation.validator;

import com.pumpkinmarket.validation.annotations.IsPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsPasswordValidator implements ConstraintValidator<IsPassword, String> {
    @Override
    public void initialize(IsPassword constraintAnnotation) {
//        constraintAnnotation.
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return value.matches("^(?=.*\\d)(?=.*[a-zA-Z]).{4,20}$");
    }
}
