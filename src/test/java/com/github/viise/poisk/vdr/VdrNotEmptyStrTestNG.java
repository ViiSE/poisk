package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Wall;
import com.github.viise.poisk.ProtectException;
import org.testng.annotations.Test;

public class VdrNotEmptyStrTestNG {

    private final Wall<String> vdrNotEmptyStr = new VdrNotEmptyStr();

    @Test
    public void validate_success_1() throws ProtectException {
        vdrNotEmptyStr.protect("Hello");
    }

    @Test
    public void validate_success_2() throws ProtectException {
        String field = "Hello!";
        vdrNotEmptyStr.protect("field", field);
    }

    @Test
    public void validate_success_checkedValueNameIsNull() throws ProtectException {
        vdrNotEmptyStr.protect(null, "Hello");
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "value is null.")
    public void validate_null_1() throws ProtectException {
        vdrNotEmptyStr.protect(null);
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "field is null.")
    public void validate_null_2() throws ProtectException {
        vdrNotEmptyStr.protect("field", null);
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "value is empty.")
    public void validate_empty_1() throws ProtectException {
        vdrNotEmptyStr.protect("");
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "field is empty.")
    public void validate_empty_2() throws ProtectException {
        vdrNotEmptyStr.protect("field", "");
    }
}
