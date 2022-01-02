package com.github.viise.poisk.tsk;

import com.github.viise.poisk.FilterList;
import com.github.viise.poisk.Task;
import com.github.viise.poisk.Wall;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.List;

/**
 * Task for filtering list of items.
 * @param <T> Task result and items to be tasked for.
 */
public final class TskFlt<T> implements Task<List<T>> {

    private final Wall<Object> vdrNotNull = new VdrNotNull();

    private final FilterList<T> flt;
    private final List<T> items;

    /**
     * Ctor.
     * @param flt {@link FilterList} Filter.
     * @param items Items to be tasked for.
     */
    public TskFlt(FilterList<T> flt, List<T> items) {
        this.flt = flt;
        this.items = items;
    }

    /**
     * Call task.
     * @return Task result.
     * @throws Exception if <code>flt</code> is null.
     */
    @Override
    public List<T> call() throws Exception {
        vdrNotNull.protect("flt", flt);
        return flt.apply(items);
    }
}
