package com.github.viise.poisk.flt;

import com.github.viise.poisk.FilterList;
import com.github.viise.poisk.ProtectException;
import com.github.viise.poisk.Wall;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FltListNormalizeFast<T> implements FilterList<T> {

    private final Wall<Object> vdrNotNull = new VdrNotNull();

    @Override
    public List<T> apply(final List<T> items) {
        try {
            vdrNotNull.protect("items", items);
            List<T> uniques = new ArrayList<>();
            Map<T, Void> uniqueMap = new HashMap<>();

            for (T item : items) {
                if (!uniqueMap.containsKey(item)) {
                    uniqueMap.put(item, null);
                }
            }

            uniqueMap.forEach((item, nothing) -> uniques.add(item) );

            return uniques;
        } catch (ProtectException e) {
            return new ArrayList<>();
        }
    }
}
