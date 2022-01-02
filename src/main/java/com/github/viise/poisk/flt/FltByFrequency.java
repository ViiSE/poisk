package com.github.viise.poisk.flt;

import com.github.viise.poisk.Filter;
import com.github.viise.poisk.ProtectException;
import com.github.viise.poisk.Wall;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class FltByFrequency<T> implements Filter<List<T>, Map<T, Integer>> {

    private final Wall<Object> vdrNotNull = new VdrNotNull();

    private final Function<Integer, Boolean> condition;

    public FltByFrequency(Function<Integer, Boolean> condition) {
        this.condition = condition;
    }

    @Override
    public List<T> apply(final Map<T, Integer> freqMap) {
        try {
            vdrNotNull.protect("condition", condition);
            vdrNotNull.protect("freqMap", freqMap);

            List<T> result = new ArrayList<>();
            freqMap.forEach((item, count) -> {
                if (condition.apply(count)) {
                    result.add(item);
                }
            });

            return result;
        } catch (ProtectException e) {
            return new ArrayList<>();
        }
    }
}
