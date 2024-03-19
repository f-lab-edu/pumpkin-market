package com.pumpkinmarket.validation.annotations;

import com.pumpkinmarket.validation.validator.IsPhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsPhoneValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsPhone {
    String message() default "Invalid phone";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String regionCode() default "KR";
}
