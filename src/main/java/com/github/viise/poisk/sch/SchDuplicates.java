package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNotEmptyList;

import java.util.ArrayList;
import java.util.List;

/**
 * Search duplicates in list of items.
 * @param <T> Search result and list of items to be searched for.
 *           Override {@link java.lang.Object#equals(T)} in custom classes for correct behaviour.
 */
public final class SchDuplicates<T> implements SearchList<T> {

    private final Validator<List<T>> vdrNotEmptyList;

    public SchDuplicates() {
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Finding duplicates in items.
     * @param items Items to find.
     * @return List of duplicates.
     * @throws NotFoundException If items is null or empty, or duplicates not found.
     */
    @Override
    public List<T> find(List<T> items) throws NotFoundException {
        try {
            vdrNotEmptyList.validate("items", items);
        } catch (ValidationException e) {
            throw new NotFoundException(e);
        }

        List<T> duplicates = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            boolean isAdd = false;
            for (int j = 0; j < items.size(); j++) {
                if (i != j) {
                    if (item.equals(items.get(j))) {
                        if (!duplicates.contains(item)) {
                            isAdd = true;
                        }
                    }
                }
            }

            if (isAdd) {
                duplicates.add(item);
            }
        }

        if (duplicates.isEmpty()) {
            throw new NotFoundException("Duplicates not found.");
        }

        return duplicates;
    }
}
