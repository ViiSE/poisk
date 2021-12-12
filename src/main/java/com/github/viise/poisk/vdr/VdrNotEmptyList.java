package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Validator;
import com.github.viise.poisk.ValidationException;

import java.util.List;

/**
 * Not empty list validation.
 */
public final class VdrNotEmptyList<T> implements Validator<List<T>> {

    private final Validator<Object> vdrNotNull = new VdrNotNull();

    /**
     * Validate list.
     * @param checkedValueName Checked list name.
     * @param checkedValue Checked list.
     * @throws ValidationException If list is null or empty.
     */
    @Override
    public void validate(String checkedValueName, List<T> checkedValue) throws ValidationException {
        String valName = checkedValueName == null ? "value" : checkedValueName;
        vdrNotNull.validate(valName, checkedValue);
        if (checkedValue.isEmpty()) {
            throw new ValidationException(valName + " is empty.");
        }
    }

    /**
     * Validate list. <code>checkedValueName</code> is "value".
     * @param checkedValue Checked list name.
     * @throws ValidationException If list is not validated.
     */
    @Override
    public void validate(List<T> checkedValue) throws ValidationException {
        validate("value", checkedValue);
    }
}
