package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Validator;
import com.github.viise.poisk.ValidationException;

/**
 * Not zero int validation.
 */
public final class VdrNotZeroInt implements Validator<Integer> {

    private final Validator<Object> vdrNotNull = new VdrNotNull();

    /**
     * Validate not zero int.
     * @param checkedValueName Checked int name.
     * @param checkedValue Checked int.
     * @throws ValidationException If int is null and zero.
     */
    @Override
    public void validate(String checkedValueName, Integer checkedValue) throws ValidationException {
        String valName = checkedValueName == null ? "value" : checkedValueName;
        vdrNotNull.validate(valName, checkedValue);
        if (checkedValue == 0) {
            throw new ValidationException(valName + " must be not zero.");
        }
    }

    /**
     * Validate not zero int. <code>checkedValueName</code> is "value".
     * @param checkedValue Checked int name.
     * @throws ValidationException If int is not validated.
     */
    @Override
    public void validate(Integer checkedValue) throws ValidationException {
        validate("value", checkedValue);
    }
}
