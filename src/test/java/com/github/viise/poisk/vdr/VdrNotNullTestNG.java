package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Validator;
import com.github.viise.poisk.ValidationException;
import org.testng.annotations.Test;

public class VdrNotNullTestNG {

    private final Validator<Object> vdrNotNull = new VdrNotNull();

    @Test
    public void validate_success_1() throws ValidationException {
        vdrNotNull.validate("Hello!");
    }

    @Test
    public void validate_success_2() throws ValidationException {
        String field = "Hello!";
        vdrNotNull.validate("field", field);
    }

    @Test
    public void validate_success_checkedValueNameIsNull() throws ValidationException {
        vdrNotNull.validate(null, "Hello!");
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "value is null.")
    public void validate_null_1() throws ValidationException {
        vdrNotNull.validate(null);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "field is null.")
    public void validate_null_2() throws ValidationException {
        vdrNotNull.validate("field", null);
    }
}
