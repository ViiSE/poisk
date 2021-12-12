package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Validator;
import com.github.viise.poisk.ValidationException;

/**
 * Not empty string validation.
 */
public final class VdrNotEmptyStr implements Validator<String> {

    private final Validator<Object> vdrNotNull = new VdrNotNull();

    /**
     * Validate string.
     * @param checkedValueName Checked string name.
     * @param checkedValue Checked string.
     * @throws ValidationException If string is null or empty.
     */
    @Override
    public void validate(String checkedValueName, String checkedValue) throws ValidationException {
        String valName = checkedValueName == null ? "value" : checkedValueName;
        vdrNotNull.validate(valName, checkedValue);
        if (checkedValue.isEmpty()) {
            throw new ValidationException(valName + " is empty.");
        }
    }

    /**
     * Validate string. <code>checkedValueName</code> is "value".
     * @param checkedValue Checked string name.
     * @throws ValidationException If string is not validated.
     */
    @Override
    public void validate(String checkedValue) throws ValidationException {
        validate("value", checkedValue);
    }
}
