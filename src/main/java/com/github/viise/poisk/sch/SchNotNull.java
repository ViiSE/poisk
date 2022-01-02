package com.github.viise.poisk.sch;

import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.ProtectException;
import com.github.viise.poisk.Wall;
import com.github.viise.poisk.vdr.VdrNotEmptyList;

import java.util.ArrayList;
import java.util.List;

/**
 * Search not null items in list of items.
 * @param <T> Search result and list of items to be searched for.
 */
public final class SchNotNull<T> implements SearchList<T> {

    private final Wall<List<T>> vdrNotEmptyList;

    public SchNotNull() {
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Finding not null items.
     * @param items Items to find.
     * @return Not null items.
     * @throws NotFoundException If items is null or empty, or not null items not found.
     */
    @Override
    public List<T> find(List<T> items) throws NotFoundException {
        try {
            vdrNotEmptyList.protect("items", items);
        } catch (ProtectException e) {
            throw new NotFoundException(e);
        }

        List<T> result = new ArrayList<>();

        for (T item: items) {
            if (item != null) {
                result.add(item);
            }
        }

        if (result.isEmpty()) {
            throw new NotFoundException("Not null items not found.");
        }

        return result;
    }
}
