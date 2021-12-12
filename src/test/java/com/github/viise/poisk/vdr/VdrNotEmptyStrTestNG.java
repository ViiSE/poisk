package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Validator;
import com.github.viise.poisk.ValidationException;
import org.testng.annotations.Test;

public class VdrNotEmptyStrTestNG {

    private final Validator<String> vdrNotEmptyStr = new VdrNotEmptyStr();

    @Test
    public void validate_success_1() throws ValidationException {
        vdrNotEmptyStr.validate("Hello");
    }

    @Test
    public void validate_success_2() throws ValidationException {
        String field = "Hello!";
        vdrNotEmptyStr.validate("field", field);
    }

    @Test
    public void validate_success_checkedValueNameIsNull() throws ValidationException {
        vdrNotEmptyStr.validate(null, "Hello");
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "value is null.")
    public void validate_null_1() throws ValidationException {
        vdrNotEmptyStr.validate(null);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "field is null.")
    public void validate_null_2() throws ValidationException {
        vdrNotEmptyStr.validate("field", null);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "value is empty.")
    public void validate_empty_1() throws ValidationException {
        vdrNotEmptyStr.validate("");
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "field is empty.")
    public void validate_empty_2() throws ValidationException {
        vdrNotEmptyStr.validate("field", "");
    }
}
