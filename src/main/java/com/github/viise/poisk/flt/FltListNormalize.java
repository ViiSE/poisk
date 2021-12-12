package com.github.viise.poisk.flt;

import com.github.viise.poisk.FilterList;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.List;

public final class FltListNormalize<T> implements FilterList<T> {

    private final Validator<Object> vdrNotNull = new VdrNotNull();

    @Override
    public List<T> apply(final List<T> items) {
        try {
            vdrNotNull.validate("items", items);
            List<T> uniques = new ArrayList<>();

            for (int i = 0; i < items.size(); i++) {
                T item = items.get(i);
                boolean isAdd = true;
                for (int j = 0; j < items.size(); j++) {
                    if (i != j) {
                        if (item.equals(items.get(j))) {
                            if (uniques.contains(item)) {
                                isAdd = false;
                            }
                        }
                    }
                }

                if (isAdd) {
                    uniques.add(item);
                }
            }

            return uniques;
        } catch (ValidationException e) {
            return new ArrayList<>();
        }
    }
}
