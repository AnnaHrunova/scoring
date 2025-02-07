package org.inbank.scoring.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class AmountValidator implements
        ConstraintValidator<AmountConstraint, String> {

    @Override
    public void initialize(AmountConstraint amount) {
    }

    @Override
    public boolean isValid(String initAmount,
                           ConstraintValidatorContext cxt) {

        if (isBlank(initAmount)) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Amount must not be blank!")
                    .addConstraintViolation();
            return false;
        }

        try {
            var amount = Integer.parseInt(initAmount);
            if (amount < 200 || amount > 5000) {
                cxt.disableDefaultConstraintViolation();
                cxt.buildConstraintViolationWithTemplate("Amount must be between 200 and 5000 EUR!")
                        .addConstraintViolation();
                return false;
            }
        } catch (Exception e) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Invalid format for amount!")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

}
