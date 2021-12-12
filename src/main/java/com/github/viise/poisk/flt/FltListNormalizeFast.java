package com.github.viise.poisk.flt;

import com.github.viise.poisk.FilterList;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FltListNormalizeFast<T> implements FilterList<T> {

    private final Validator<Object> vdrNotNull = new VdrNotNull();

    @Override
    public List<T> apply(final List<T> items) {
        try {
            vdrNotNull.validate("items", items);
            List<T> uniques = new ArrayList<>();
            Map<T, Void> uniqueMap = new HashMap<>();

            for (T item : items) {
                if (!uniqueMap.containsKey(item)) {
                    uniqueMap.put(item, null);
                }
            }

            uniqueMap.forEach((item, nothing) -> uniques.add(item) );

            return uniques;
        } catch (ValidationException e) {
            return new ArrayList<>();
        }
    }
}
