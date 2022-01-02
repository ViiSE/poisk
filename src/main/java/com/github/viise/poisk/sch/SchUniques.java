package com.github.viise.poisk.sch;

import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.ProtectException;
import com.github.viise.poisk.Wall;
import com.github.viise.poisk.vdr.VdrNotEmptyList;

import java.util.ArrayList;
import java.util.List;

/**
 * Search uniques in list of items.
 * @param <T> Search result and list of items to be searched for.
 *           Override {@link java.lang.Object#equals(T)} in custom classes for correct behaviour.
 */
public final class SchUniques<T> implements SearchList<T> {

    private final Wall<List<T>> vdrNotEmptyList;

    public SchUniques() {
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Finding uniques in items.
     * @param items Items to find.
     * @return List of duplicates.
     * @throws NotFoundException If items is null or empty, or uniques not found.
     */
    @Override
    public List<T> find(List<T> items) throws NotFoundException {
        try {
            vdrNotEmptyList.protect("items", items);
        } catch (ProtectException e) {
            throw new NotFoundException(e);
        }
        List<T> uniques = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            boolean isAdd = true;
            for (int j = 0; j < items.size(); j++) {
                if (i != j) {
                    if (item.equals(items.get(j))) {
                        isAdd = false;
                        break;
                    }
                }
            }

            if (isAdd) {
                uniques.add(item);
            }
        }

        if (uniques.isEmpty()) {
            throw new NotFoundException("Uniques not found.");
        }

        return uniques;
    }
}
