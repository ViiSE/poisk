package com.github.viise.poisk.sch;

import com.github.viise.poisk.*;
import com.github.viise.poisk.vdr.VdrNotEmptyList;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Search less common items in list of items.
 *
 * For example:
 * <code>
 *     (pseudo-code)
 *
 *     List words = new List("One", "Two", "Two", "Three");
 *     List res = new SchLessCommonItems().find(words);
 *
 *     res:
 *     One
 *     Three
 * </code>
 * @param <T> Override {@link java.lang.Object#equals(T)} in custom classes for correct behaviour.
 */
public final class SchLessCommonItems<T> implements SearchList<T> {

    private final Validator<Object> vdrNotNull;
    private final Validator<List<T>> vdrNotEmptyList;

    private final Search<Map<T, Integer>, List<T>> schFreq;

    /**
     * Ctor.
     * @param schFreq Search impl for finding frequency
     *                (default value {@link com.github.viise.poisk.sch.SchFrequency}).
     */
    public SchLessCommonItems(Search<Map<T, Integer>, List<T>> schFreq) {
        this.schFreq = schFreq;

        this.vdrNotNull = new VdrNotNull();
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Ctor.
     */
    public SchLessCommonItems() {
        this(new SchFrequency<>());
    }

    /**
     * Finding less common items in list of items.
     * @param items Items to find.
     * @return Less common items.
     * @throws NotFoundException If schFreq is null, items is null or empty.
     */
    @Override
    public List<T> find(List<T> items) throws NotFoundException {
        try {
            vdrNotNull.validate("schFreq", schFreq);
            vdrNotEmptyList.validate("items", items);
        } catch (ValidationException e) {
            throw new NotFoundException(e);
        }

        AtomicReference<List<T>> itemsRef = new AtomicReference<>(new ArrayList<>());
        AtomicInteger freqRef = new AtomicInteger(Integer.MAX_VALUE);
        Map<T, Integer> freqMap = schFreq.find(items);

        freqMap.forEach((item, freq) -> {
            if (freq < freqRef.get()) {
                freqRef.set(freq);
                itemsRef.set(new ArrayList<>());
                itemsRef.get().add(item);
            } else if (freq == freqRef.get()) {
                itemsRef.get().add(item);
            }
        });

        return itemsRef.get();
    }
}
