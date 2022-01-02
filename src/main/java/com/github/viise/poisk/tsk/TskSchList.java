package com.github.viise.poisk.tsk;

import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.Task;
import com.github.viise.poisk.Wall;
import com.github.viise.poisk.vdr.VdrNotEmptyList;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.List;

/**
 * Task for searching list of items.
 * @param <T> Task result and items to be tasked for.
 */
public final class TskSchList<T> implements Task<List<T>> {

    private final Wall<Object> vdrNotNull = new VdrNotNull();
    private final Wall<List<T>> vdrNotEmptyList = new VdrNotEmptyList<>();

    private final SearchList<T> sch;
    private final List<T> items;

    /**
     * Ctor.
     * @param sch {@link SearchList} Search.
     * @param items Items to be tasked for.
     */
    public TskSchList(SearchList<T> sch, List<T> items) {
        this.sch = sch;
        this.items = items;
    }

    /**
     * Call task.
     * @return Task result.
     * @throws Exception if <code>sch</code> is null or <code>items</code> is null or empty.
     */
    @Override
    public List<T> call() throws Exception {
        vdrNotNull.protect("sch", sch);
        vdrNotEmptyList.protect("items", items);

        return sch.find(items);
    }
}
