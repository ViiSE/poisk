package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Wall;
import com.github.viise.poisk.ProtectException;

import java.util.List;

/**
 * Not empty list validation.
 */
public final class VdrNotEmptyList<T> implements Wall<List<T>> {

    private final Wall<Object> vdrNotNull = new VdrNotNull();

    /**
     * Validate list.
     * @param checkedValueName Checked list name.
     * @param checkedValue Checked list.
     * @throws ProtectException If list is null or empty.
     */
    @Override
    public void protect(String checkedValueName, List<T> checkedValue) throws ProtectException {
        String valName = checkedValueName == null ? "value" : checkedValueName;
        vdrNotNull.protect(valName, checkedValue);
        if (checkedValue.isEmpty()) {
            throw new ProtectException(valName + " is empty.");
        }
    }

    /**
     * Validate list. <code>checkedValueName</code> is "value".
     * @param checkedValue Checked list name.
     * @throws ProtectException If list is not validated.
     */
    @Override
    public void protect(List<T> checkedValue) throws ProtectException {
        protect("value", checkedValue);
    }
}
