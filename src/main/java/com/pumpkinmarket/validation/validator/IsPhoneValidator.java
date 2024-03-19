package com.pumpkinmarket.validation.validator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.pumpkinmarket.validation.annotations.IsPhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsPhoneValidator implements ConstraintValidator<IsPhone, String> {
    private final PhoneNumberUtil phoneNumberUtil;
    private String regionCode;

    public IsPhoneValidator(PhoneNumberUtil phoneNumberUtil) {
        this.phoneNumberUtil = phoneNumberUtil;
    }

    @Override
    public void initialize(IsPhone constraintAnnotation) {
        this.regionCode = constraintAnnotation.regionCode();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        try {
            final var parsePhone = this.phoneNumberUtil.parse(value, this.regionCode);

            return phoneNumberUtil.isValidNumberForRegion(parsePhone, this.regionCode);
        } catch (NumberParseException e) {
            return false;
        }
    }
}
