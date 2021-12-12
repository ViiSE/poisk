package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Validator;
import com.github.viise.poisk.ValidationException;

/**
 * Natural int validation.
 */
public final class VdrNaturalInt implements Validator<Integer> {

    private final Validator<Object> vdrNotNull = new VdrNotNull();
    private final Validator<Integer> vdrPositiveInt = new VdrPositiveInt();
    private final Validator<Integer> vdrNotZeroInt = new VdrNotZeroInt();

    /**
     * Validate natural int.
     * @param checkedValueName Checked natural int name.
     * @param checkedValue Checked natural int.
     * @throws ValidationException If natural is null, zero or negative.
     */
    @Override
    public void validate(String checkedValueName, Integer checkedValue) throws ValidationException {
        String valName = checkedValueName == null ? "value" : checkedValueName;
        vdrNotNull.validate(valName, checkedValue);
        vdrNotZeroInt.validate(valName, checkedValue);
        vdrPositiveInt.validate(valName, checkedValue);
    }

    /**
     * Validate natural int. <code>checkedValueName</code> is "value".
     * @param checkedValue Checked natural int.
     * @throws ValidationException If natural int is not validated.
     */
    @Override
    public void validate(Integer checkedValue) throws ValidationException {
        validate("value", checkedValue);
    }
}
