package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Wall;
import com.github.viise.poisk.ProtectException;
import org.testng.annotations.Test;

public class VdrPositiveIntTestNG {

    private final Wall<Integer> vdrPositiveInt = new VdrPositiveInt();

    @Test
    public void validate_success_1() throws ProtectException {
        vdrPositiveInt.protect(1);
    }

    @Test
    public void validate_success_2() throws ProtectException {
        Integer field = 1;
        vdrPositiveInt.protect("field", field);
    }

    @Test
    public void validate_success_checkedValueNameIsNull() throws ProtectException {
        vdrPositiveInt.protect(null, 1);
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "value must be positive.")
    public void validate_negativevalue_1() throws ProtectException {
        vdrPositiveInt.protect(-1);
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "field must be positive.")
    public void validate_negativevalue_2() throws ProtectException {
        vdrPositiveInt.protect("field", -1);
    }
}
