package com.github.viise.poisk.sch;

import com.github.viise.poisk.Search;
import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNotEmptyList;

import java.util.List;

/**
 * Search first duplicate in list of items.
 * @param <T> Search result and list of items to be searched for.
 *           Override {@link java.lang.Object#equals(T)} in custom classes for correct behaviour.
 */
public final class SchFirstDuplicate<T> implements Search<T, List<T>> {

    private final Validator<List<T>> vdrNotEmptyList;

    public SchFirstDuplicate() {
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Finding first duplicate in list of items
     * @param items Items to find.
     * @return First duplicate.
     * @throws NotFoundException If items is null or empty, or duplicate not found.
     */
    @Override
    public T find(List<T> items) throws NotFoundException {
        try {
            vdrNotEmptyList.validate("items", items);
        } catch (ValidationException e) {
            throw new NotFoundException(e);
        }

        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            for (int j = 0; j < items.size(); j++) {
                if (i != j) {
                    if (item.equals(items.get(j))) {
                        return item;
                    }
                }
            }
        }

        throw new NotFoundException("Duplicate not found.");
    }
}
