package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Wall;
import com.github.viise.poisk.ProtectException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VdrNotEmptyListTestNG {

    private final Wall<List<String>> vdrNotEmptyList = new VdrNotEmptyList<>();
    
    @Test
    public void validate_success() throws ProtectException {
        vdrNotEmptyList.protect(new ArrayList<String>() {{ add("Hello!"); }});
    }

    @Test
    public void validate_success_2() throws ProtectException {
        vdrNotEmptyList.protect("items", new ArrayList<String>() {{ add("Hello!"); }});
    }

    @Test
    public void validate_success_checkedValueNameIsNull() throws ProtectException {
        vdrNotEmptyList.protect(null, new ArrayList<String>() {{ add("Hello!"); }});
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "value is null.")
    public void validate_null_1() throws ProtectException {
        vdrNotEmptyList.protect(null);
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "field is null.")
    public void validate_null_2() throws ProtectException {
        vdrNotEmptyList.protect("field", null);
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "value is empty.")
    public void validate_empty_1() throws ProtectException {
        vdrNotEmptyList.protect(new ArrayList<>());
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "field is empty.")
    public void validate_empty_2() throws ProtectException {
        vdrNotEmptyList.protect("field", new ArrayList<>());
    }
}
