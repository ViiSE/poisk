package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Validator;
import com.github.viise.poisk.ValidationException;
import org.testng.annotations.Test;

public class VdrNotZeroIntTestNG {

    private final Validator<Integer> vdrNotZeroInt = new VdrNotZeroInt();

    @Test
    public void validate_success_1() throws ValidationException {
        vdrNotZeroInt.validate(1);
    }

    @Test
    public void validate_success_2() throws ValidationException {
        Integer field = 1;
        vdrNotZeroInt.validate("field", field);
    }

    @Test
    public void validate_success_checkedValueNameIsNull() throws ValidationException {
        vdrNotZeroInt.validate(null, 1);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "value must be not zero.")
    public void validate_zerovalue_1() throws ValidationException {
        vdrNotZeroInt.validate(0);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "field must be not zero.")
    public void validate_zerovalue_2() throws ValidationException {
        vdrNotZeroInt.validate("field", 0);
    }
}
