package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Wall;
import com.github.viise.poisk.ProtectException;

/**
 * Not zero int validation.
 */
public final class VdrNotZeroInt implements Wall<Integer> {

    private final Wall<Object> vdrNotNull = new VdrNotNull();

    /**
     * Validate not zero int.
     * @param checkedValueName Checked int name.
     * @param checkedValue Checked int.
     * @throws ProtectException If int is null and zero.
     */
    @Override
    public void protect(String checkedValueName, Integer checkedValue) throws ProtectException {
        String valName = checkedValueName == null ? "value" : checkedValueName;
        vdrNotNull.protect(valName, checkedValue);
        if (checkedValue == 0) {
            throw new ProtectException(valName + " must be not zero.");
        }
    }

    /**
     * Validate not zero int. <code>checkedValueName</code> is "value".
     * @param checkedValue Checked int name.
     * @throws ProtectException If int is not validated.
     */
    @Override
    public void protect(Integer checkedValue) throws ProtectException {
        protect("value", checkedValue);
    }
}
