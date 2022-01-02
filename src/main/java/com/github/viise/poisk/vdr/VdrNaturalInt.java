package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Wall;
import com.github.viise.poisk.ProtectException;

/**
 * Natural int validation.
 */
public final class VdrNaturalInt implements Wall<Integer> {

    private final Wall<Object> vdrNotNull = new VdrNotNull();
    private final Wall<Integer> vdrPositiveInt = new VdrPositiveInt();
    private final Wall<Integer> vdrNotZeroInt = new VdrNotZeroInt();

    /**
     * Validate natural int.
     * @param checkedValueName Checked natural int name.
     * @param checkedValue Checked natural int.
     * @throws ProtectException If natural is null, zero or negative.
     */
    @Override
    public void protect(String checkedValueName, Integer checkedValue) throws ProtectException {
        String valName = checkedValueName == null ? "value" : checkedValueName;
        vdrNotNull.protect(valName, checkedValue);
        vdrNotZeroInt.protect(valName, checkedValue);
        vdrPositiveInt.protect(valName, checkedValue);
    }

    /**
     * Validate natural int. <code>checkedValueName</code> is "value".
     * @param checkedValue Checked natural int.
     * @throws ProtectException If natural int is not validated.
     */
    @Override
    public void protect(Integer checkedValue) throws ProtectException {
        protect("value", checkedValue);
    }
}
