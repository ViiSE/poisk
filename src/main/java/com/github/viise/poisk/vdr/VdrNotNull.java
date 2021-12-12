package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Validator;
import com.github.viise.poisk.ValidationException;

/**
 * Not null object validation.
 */
public final class VdrNotNull implements Validator<Object> {

    /**
     * Validate object.
     * @param checkedValueName Checked object name.
     * @param checkedValue Checked object.
     * @throws ValidationException If object is null.
     */
    @Override
    public void validate(String checkedValueName, Object checkedValue) throws ValidationException {
        String valName = checkedValueName == null ? "value" : checkedValueName;
        if (checkedValue == null) {
            throw new ValidationException(valName + " is null.");
        }
    }

    /**
     * Validate object. <code>checkedValueName</code> is "value".
     * @param checkedValue Checked object name.
     * @throws ValidationException If object is not validated.
     */
    @Override
    public void validate(Object checkedValue) throws ValidationException {
        validate("value", checkedValue);
    }
}
