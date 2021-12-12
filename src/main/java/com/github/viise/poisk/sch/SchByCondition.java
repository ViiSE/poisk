package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNotEmptyList;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Search list of items by condition.
 *
 * For example, we have a list of 1,2,3,4,5. We want to found all items that more than 3:
 * <code>
 * List result = new SchByCondition(item -> item > 3).find(items);
 * </code>
 * Result is 4,5.
 *
 * @param <T> Override {@link java.lang.Object#equals(T)} in custom classes for correct behaviour.
 */
public final class SchByCondition<T> implements SearchList<T> {

    private final Validator<Object> vdrNotNull;
    private final Validator<List<T>> vdrNotEmptyList;

    private final Function<T, Boolean> condition;

    /**
     * Ctor.
     * @param condition Search condition.
     */
    public SchByCondition(Function<T, Boolean> condition) {
        this.condition = condition;
        this.vdrNotNull = new VdrNotNull();
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Finding items by condition.
     * @param items Items to find.
     * @return Founded items by condition.
     * @throws NotFoundException If condition is null, items is null or empty, or items not found.
     */
    @Override
    public List<T> find(final List<T> items) throws NotFoundException {
        try {
            vdrNotNull.validate("condition", condition);
            vdrNotEmptyList.validate("items", items);
        } catch (ValidationException e) {
            throw new NotFoundException(e);
        }

        List<T> result = new ArrayList<>();

        for (T item: items) {
            if (item != null) {
                if(condition.apply(item)) {
                    result.add(item);
                }
            }
        }

        if (result.isEmpty()) {
            throw new NotFoundException("lookingItem not found.");
        }

        return result;
    }
}
