package com.github.viise.poisk.sch;

import com.github.viise.poisk.Search;
import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNotEmptyList;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Fast search uniques in list of items.
 * Consumes more memory but faster than {@link com.github.viise.poisk.sch.SchUniques}.
 * In multi thread, is does not greatly outperform {@link com.github.viise.poisk.sch.SchUniques}.
 * Better to use in single thread.
 * @param <T> Search result and list of items to be searched for.
 *           Override {@link java.lang.Object#equals(T)} in custom classes for correct behaviour.
 */
public final class SchUniquesFast<T> implements SearchList<T> {

    private final Validator<Object> vdrNotNull;
    private final Validator<List<T>> vdrNotEmptyList;

    private final Search<Map<T, Integer>, List<T>> schFreq;

    /**
     * Ctor.
     * @param schFreq Search impl for finding frequency
     *                (default value {@link com.github.viise.poisk.sch.SchFrequency}).
     */
    public SchUniquesFast(Search<Map<T, Integer>, List<T>> schFreq) {
        this.schFreq = schFreq;

        this.vdrNotNull = new VdrNotNull();
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Ctor.
     */
    public SchUniquesFast() {
        this(new SchFrequency<>());
    }

    /**
     * Finding uniques in items.
     * @param items Items to find.
     * @return List of uniques.
     * @throws NotFoundException If items is null or empty, or uniques not found.
     */
    @Override
    public List<T> find(List<T> items) throws NotFoundException {
        try {
            vdrNotNull.validate("schFreq", schFreq);
            vdrNotEmptyList.validate("items", items);
        } catch (ValidationException e) {
            throw new NotFoundException(e);
        }
        List<T> uniques = new ArrayList<>();
        Map<T, Integer> freqMap = schFreq.find(items);

        freqMap.forEach((item, count) -> {
            if (item != null) {
                if (count == 1) {
                    uniques.add(item);
                }
            }
        });

        if (uniques.isEmpty()) {
            throw new NotFoundException("Uniques not found.");
        }

        return uniques;
    }
}
