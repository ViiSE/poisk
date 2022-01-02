package com.github.viise.poisk.sch;

import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.ProtectException;
import com.github.viise.poisk.Wall;
import com.github.viise.poisk.vdr.VdrNotEmptyList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Search items by items list.
 * Search result and list of items to be chunked for.
 * @param <T> Override {@link java.lang.Object#equals(T)} in custom classes for correct behaviour.
 */
public final class SchItems<T> implements SearchList<T> {

    private final Wall<List<T>> vdrNotEmptyList;

    private final List<T> items;

    /**
     * Ctor.
     * @param items Source list of items.
     */
    public SchItems(List<T> items) {
        this.items = items;

        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Ctor.
     * @param items Source list of items.
     */
    @SafeVarargs
    public SchItems(T... items) {
        this(Arrays.asList(items));
    }

    /**
     * Finding <code>lookingItems</code> in <code>items</code>.
     * @param lookingItems Items to find.
     * @return <code>lookingItems</code> like search result.
     * @throws NotFoundException If <code>lookingItems</code> is null or empty, <code>items</code> is null or empty,
     * or <code>lookingItems</code> is not found.
     */
    @Override
    public List<T> find(List<T> lookingItems) throws NotFoundException {
        try {
            vdrNotEmptyList.protect("items", items);
            vdrNotEmptyList.protect("lookingItems", lookingItems);
        } catch (ProtectException e) {
            throw new NotFoundException(e);
        }

        List<T> result = new ArrayList<>();
        for (T item: items) {
            for (T lookingItem: lookingItems) {
                if (item != null && lookingItem != null) {
                    if (item.equals(lookingItem)) {
                        result.add(lookingItem);
                    }
                }
            }
        }

        if (result.isEmpty()) {
            throw new NotFoundException("Looking items " + lookingItems + " not found.");
        }

        return result;
    }
}
