package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Wall;
import com.github.viise.poisk.ProtectException;
import org.testng.annotations.Test;

public class VdrNotZeroIntTestNG {

    private final Wall<Integer> vdrNotZeroInt = new VdrNotZeroInt();

    @Test
    public void validate_success_1() throws ProtectException {
        vdrNotZeroInt.protect(1);
    }

    @Test
    public void validate_success_2() throws ProtectException {
        Integer field = 1;
        vdrNotZeroInt.protect("field", field);
    }

    @Test
    public void validate_success_checkedValueNameIsNull() throws ProtectException {
        vdrNotZeroInt.protect(null, 1);
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "value must be not zero.")
    public void validate_zerovalue_1() throws ProtectException {
        vdrNotZeroInt.protect(0);
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "field must be not zero.")
    public void validate_zerovalue_2() throws ProtectException {
        vdrNotZeroInt.protect("field", 0);
    }
}
