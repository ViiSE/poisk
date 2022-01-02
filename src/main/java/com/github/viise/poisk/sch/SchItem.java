package com.github.viise.poisk.sch;

import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.Search;
import com.github.viise.poisk.ProtectException;
import com.github.viise.poisk.Wall;
import com.github.viise.poisk.vdr.VdrNotEmptyList;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.List;

/**
 * Search item by items list.
 * @param <T> Search result and list of items to be searched for.
 *           Override {@link java.lang.Object#equals(T)} in custom classes for correct behaviour.
 */
public final class SchItem<T> implements Search<T, T> {

    private final Wall<Object> vdrNotNull;
    private final Wall<List<T>> vdrNotEmptyList;

    private final List<T> items;

    /**
     * Ctor.
     * @param items Source list of items.
     */
    public SchItem(List<T> items) {
        this.items = items;

        this.vdrNotNull = new VdrNotNull();
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Finding <code>lookingItem</code> in <code>items</code>.
     * @param lookingItem Item to find.
     * @return <code>lookingItem</code> like search result.
     * @throws NotFoundException If <code>lookingItem</code> is null, <code>items</code> is null or empty, or
     * <code>lookingItem</code> is not found.
     */
    @Override
    public T find(T lookingItem) throws NotFoundException {
        try {
            vdrNotNull.protect("lookingItem", lookingItem);
            vdrNotEmptyList.protect("items", items);
        } catch (ProtectException e) {
            throw new NotFoundException(e);
        }

        for (T item: items) {
            if (item != null) {
                if (item.equals(lookingItem)) {
                    return lookingItem;
                }
            }
        }

        throw new NotFoundException("lookingItem " + lookingItem + " not found.");
    }
}
