package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Validator;
import com.github.viise.poisk.ValidationException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VdrNotEmptyListTestNG {

    private final Validator<List<String>> vdrNotEmptyList = new VdrNotEmptyList<>();
    
    @Test
    public void validate_success() throws ValidationException {
        vdrNotEmptyList.validate(new ArrayList<String>() {{ add("Hello!"); }});
    }

    @Test
    public void validate_success_2() throws ValidationException {
        vdrNotEmptyList.validate("items", new ArrayList<String>() {{ add("Hello!"); }});
    }

    @Test
    public void validate_success_checkedValueNameIsNull() throws ValidationException {
        vdrNotEmptyList.validate(null, new ArrayList<String>() {{ add("Hello!"); }});
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "value is null.")
    public void validate_null_1() throws ValidationException {
        vdrNotEmptyList.validate(null);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "field is null.")
    public void validate_null_2() throws ValidationException {
        vdrNotEmptyList.validate("field", null);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "value is empty.")
    public void validate_empty_1() throws ValidationException {
        vdrNotEmptyList.validate(new ArrayList<>());
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "field is empty.")
    public void validate_empty_2() throws ValidationException {
        vdrNotEmptyList.validate("field", new ArrayList<>());
    }
}
