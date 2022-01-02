package com.github.viise.poisk.ord;

import com.github.viise.poisk.Order;
import com.github.viise.poisk.ProtectException;
import com.github.viise.poisk.Wall;
import com.github.viise.poisk.vdr.VdrNotEmptyList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parallel order by ascending.
 * Use when items size is large (> 1 million).
 * @param <T> Order result and items to be ordered for. <code>T</code> must extends {@link java.lang.Comparable}.
 */
public final class OrdAscParallel<T extends Comparable<? super T>> implements Order<List<T>> {

    private final Wall<List<T>> vdrNotEmptyList = new VdrNotEmptyList<>();

    /**
     * @param items Items to be sorted for.
     * @return Empty list if <code>items</code> is empty or null, otherwise sort result.
     */
    @Override
    public List<T> sort(final List<T> items) {
        try {
            vdrNotEmptyList.protect("items", items);
        } catch (ProtectException e) {
            return items == null ? new ArrayList<>() : items;
        }

        return items.parallelStream()
                .sorted()
                .collect(Collectors.toList());
    }
}
