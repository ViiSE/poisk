package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Validator;
import com.github.viise.poisk.ValidationException;
import org.testng.annotations.Test;

public class VdrPositiveIntTestNG {

    private final Validator<Integer> vdrPositiveInt = new VdrPositiveInt();

    @Test
    public void validate_success_1() throws ValidationException {
        vdrPositiveInt.validate(1);
    }

    @Test
    public void validate_success_2() throws ValidationException {
        Integer field = 1;
        vdrPositiveInt.validate("field", field);
    }

    @Test
    public void validate_success_checkedValueNameIsNull() throws ValidationException {
        vdrPositiveInt.validate(null, 1);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "value must be positive.")
    public void validate_negativevalue_1() throws ValidationException {
        vdrPositiveInt.validate(-1);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "field must be positive.")
    public void validate_negativevalue_2() throws ValidationException {
        vdrPositiveInt.validate("field", -1);
    }
}
