package com.github.viise.poisk.ord;

import com.github.viise.poisk.Order;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNotEmptyList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Order by ascending.
 * @param <T> Order result and items to be ordered for. <code>T</code> must extends {@link java.lang.Comparable}.
 */
public final class OrdAsc<T extends Comparable<? super T>> implements Order<List<T>> {

    private final Validator<List<T>> vdrNotEmptyList = new VdrNotEmptyList<>();

    /**
     * @param items Items to be sorted for.
     * @return Empty list if <code>items</code> is empty or null, otherwise sort result.
     */
    @Override
    public List<T> sort(final List<T> items) {
        try {
            vdrNotEmptyList.validate("items", items);
        } catch (ValidationException e) {
            return items == null ? new ArrayList<>() : items;
        }

        return items.stream()
                .sorted()
                .collect(Collectors.toList());
    }
}
