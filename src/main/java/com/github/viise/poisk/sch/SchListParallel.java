package com.github.viise.poisk.sch;

import com.github.viise.poisk.*;
import com.github.viise.poisk.chk.ChkList;
import com.github.viise.poisk.tsk.TskParallel;
import com.github.viise.poisk.tsk.TskSchList;
import com.github.viise.poisk.vdr.VdrNaturalInt;
import com.github.viise.poisk.vdr.VdrNotEmptyList;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Parallel search by list of items.
 * Decorator of any {@link SearchList} implementation for multi threading searching.
 * @param <T> Search result and list of items to be searched for.
 *           Override {@link java.lang.Object#equals(T)} in custom classes for correct behaviour.
 */
public final class SchListParallel<T> implements SearchList<T> {

    private final Validator<Object> vdrNotNull;
    private final Validator<Integer> vdrNaturalInt;
    private final Validator<List<T>> vdrNotEmptyList;

    private final SearchList<T> sch;
    private final Integer threadCount;

    /**
     * Ctor.
     * @param sch {@link SearchList} impl for searching.
     * @param threadCount Thread count (default value 2).
     */
    public SchListParallel(SearchList<T> sch, Integer threadCount) {
        this.sch = sch;
        this.threadCount = threadCount;

        this.vdrNotNull = new VdrNotNull();
        this.vdrNaturalInt = new VdrNaturalInt();
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Ctor.
     * @param sch {@link SearchList} impl for searching.
     */
    public SchListParallel(SearchList<T> sch) {
        this(sch, 2);
    }

    /**
     * Finding items.
     * @param items Items to find.
     * @return Founded items.
     * @throws NotFoundException If sch is null, threadCount is not natural int, items is null or empty, or looking
     * items not found.
     */
    @Override
    public List<T> find(List<T> items) throws NotFoundException {
        try {
            vdrNotNull.validate("sch", sch);
            vdrNaturalInt.validate("threadCount", threadCount);
            vdrNotEmptyList.validate("items", items);

            List<List<T>> chunkedItemsList = new ChkList<T>(threadCount).divide(items);
            List<Task<List<T>>> tskLists = new ArrayList<>();

            for (List<T> chunkedItems : chunkedItemsList) {
                tskLists.add(new TskSchList<>(sch, chunkedItems));
            }

            try {
                return new TskParallel<>(tskLists, threadCount).call();
            } catch (Exception e) {
                throw new NotFoundException(e);
            }
        } catch (DivideException | ValidationException e) {
            throw new NotFoundException(e);
        }
    }
}
