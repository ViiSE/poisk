package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Validator;
import com.github.viise.poisk.ValidationException;
import org.testng.annotations.Test;

public class VdrNaturalIntTestNG {

    private final Validator<Integer> vdrNaturalInt = new VdrNaturalInt();
    
    @Test
    public void validate_success_1() throws ValidationException {
        vdrNaturalInt.validate(1);
    }

    @Test
    public void validate_success_2() throws ValidationException {
        Integer field = 1;
        vdrNaturalInt.validate("field", field);
    }

    @Test
    public void validate_success_checkedValueNameIsNull() throws ValidationException {
        Integer field = 1;
        vdrNaturalInt.validate(null, field);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "value must be positive.")
    public void validate_negativeValue_1() throws ValidationException {
        vdrNaturalInt.validate(-1);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "field must be positive.")
    public void validate_negativeValue_2() throws ValidationException {
        vdrNaturalInt.validate("field", -1);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "value must be not zero.")
    public void validate_zeroValue_1() throws ValidationException {
        vdrNaturalInt.validate(0);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "field must be not zero.")
    public void validate_zeroValue_2() throws ValidationException {
        vdrNaturalInt.validate("field", 0);
    }
}
