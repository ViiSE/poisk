package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Wall;
import com.github.viise.poisk.ProtectException;

/**
 * Not null object validation.
 */
public final class VdrNotNull implements Wall<Object> {

    /**
     * Validate object.
     * @param checkedValueName Checked object name.
     * @param checkedValue Checked object.
     * @throws ProtectException If object is null.
     */
    @Override
    public void protect(String checkedValueName, Object checkedValue) throws ProtectException {
        String valName = checkedValueName == null ? "value" : checkedValueName;
        if (checkedValue == null) {
            throw new ProtectException(valName + " is null.");
        }
    }

    /**
     * Validate object. <code>checkedValueName</code> is "value".
     * @param checkedValue Checked object name.
     * @throws ProtectException If object is not validated.
     */
    @Override
    public void protect(Object checkedValue) throws ProtectException {
        protect("value", checkedValue);
    }
}
