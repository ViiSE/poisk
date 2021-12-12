package com.github.viise.poisk.sch;

import com.github.viise.poisk.Search;
import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNotEmptyList;

import java.util.List;

/**
 * Search first unique in list of items.
 * @param <T> Search result and list of items to be searched for.
 *           Override {@link java.lang.Object#equals(T)} in custom classes for correct behaviour.
 */
public final class SchFirstUnique<T> implements Search<T, List<T>> {

    private final Validator<List<T>> vdrNotEmptyList;

    public SchFirstUnique() {
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Finding first unique in list of items
     * @param items Items to find.
     * @return First unique.
     * @throws NotFoundException If items is null or empty, or unique not found.
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
                return item;
            }
        }

        throw new NotFoundException("Uniques not found.");
    }
}
