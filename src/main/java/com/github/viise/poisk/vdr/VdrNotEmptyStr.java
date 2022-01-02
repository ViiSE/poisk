package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Wall;
import com.github.viise.poisk.ProtectException;

/**
 * Not empty string validation.
 */
public final class VdrNotEmptyStr implements Wall<String> {

    private final Wall<Object> vdrNotNull = new VdrNotNull();

    /**
     * Validate string.
     * @param checkedValueName Checked string name.
     * @param checkedValue Checked string.
     * @throws ProtectException If string is null or empty.
     */
    @Override
    public void protect(String checkedValueName, String checkedValue) throws ProtectException {
        String valName = checkedValueName == null ? "value" : checkedValueName;
        vdrNotNull.protect(valName, checkedValue);
        if (checkedValue.isEmpty()) {
            throw new ProtectException(valName + " is empty.");
        }
    }

    /**
     * Validate string. <code>checkedValueName</code> is "value".
     * @param checkedValue Checked string name.
     * @throws ProtectException If string is not validated.
     */
    @Override
    public void protect(String checkedValue) throws ProtectException {
        protect("value", checkedValue);
    }
}
