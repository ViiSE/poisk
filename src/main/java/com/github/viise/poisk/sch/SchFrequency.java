package com.github.viise.poisk.sch;

import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.Search;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNotEmptyList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Search items frequency by list of items.
 *
 * For example:
 * <code>
 *     (pseudo-code)
 *
 *     List words = new List("One", "Two", "Two");
 *     Map res = new SchFrequency().find(words);
 *
 *     res:
 *     One -> 1
 *     Two -> 2
 * </code>
 *
 * @param <T> Override {@link java.lang.Object#equals(T)} in custom classes for correct behaviour.
 */
public final class SchFrequency<T> implements Search<Map<T, Integer>, List<T>> {

    private final Validator<List<T>> vdrNotEmptyList;

    public SchFrequency() {
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Finding items frequency by list of items.
     * @param items Items to find.
     * @return Items frequency.
     * @throws NotFoundException If items is null or empty.
     */
    @Override
    public Map<T, Integer> find(List<T> items) throws NotFoundException {
        try {
            vdrNotEmptyList.validate("items", items);
        } catch (ValidationException e) {
            throw new NotFoundException(e);
        }

        Map<T, Integer> frequencyMap = new HashMap<>();

        for (T item : items) {
            frequencyMap.put(
                    item,
                    frequencyMap.getOrDefault(item, 0) + 1
            );
        }

        return frequencyMap;
    }
}
