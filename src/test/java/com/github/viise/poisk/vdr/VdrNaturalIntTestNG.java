package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Wall;
import com.github.viise.poisk.ProtectException;
import org.testng.annotations.Test;

public class VdrNaturalIntTestNG {

    private final Wall<Integer> vdrNaturalInt = new VdrNaturalInt();
    
    @Test
    public void validate_success_1() throws ProtectException {
        vdrNaturalInt.protect(1);
    }

    @Test
    public void validate_success_2() throws ProtectException {
        Integer field = 1;
        vdrNaturalInt.protect("field", field);
    }

    @Test
    public void validate_success_checkedValueNameIsNull() throws ProtectException {
        Integer field = 1;
        vdrNaturalInt.protect(null, field);
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "value must be positive.")
    public void validate_negativeValue_1() throws ProtectException {
        vdrNaturalInt.protect(-1);
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "field must be positive.")
    public void validate_negativeValue_2() throws ProtectException {
        vdrNaturalInt.protect("field", -1);
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "value must be not zero.")
    public void validate_zeroValue_1() throws ProtectException {
        vdrNaturalInt.protect(0);
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "field must be not zero.")
    public void validate_zeroValue_2() throws ProtectException {
        vdrNaturalInt.protect("field", 0);
    }
}
