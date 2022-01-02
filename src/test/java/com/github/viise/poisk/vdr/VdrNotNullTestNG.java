package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Wall;
import com.github.viise.poisk.ProtectException;
import org.testng.annotations.Test;

public class VdrNotNullTestNG {

    private final Wall<Object> vdrNotNull = new VdrNotNull();

    @Test
    public void validate_success_1() throws ProtectException {
        vdrNotNull.protect("Hello!");
    }

    @Test
    public void validate_success_2() throws ProtectException {
        String field = "Hello!";
        vdrNotNull.protect("field", field);
    }

    @Test
    public void validate_success_checkedValueNameIsNull() throws ProtectException {
        vdrNotNull.protect(null, "Hello!");
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "value is null.")
    public void validate_null_1() throws ProtectException {
        vdrNotNull.protect(null);
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "field is null.")
    public void validate_null_2() throws ProtectException {
        vdrNotNull.protect("field", null);
    }
}
