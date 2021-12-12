package com.github.viise.poisk.sch;

import com.github.viise.poisk.*;
import com.github.viise.poisk.vdr.VdrNaturalInt;
import com.github.viise.poisk.vdr.VdrNotEmptyList;
import com.github.viise.poisk.vdr.VdrNotNull;
import com.github.viise.poisk.vdr.VdrPositiveInt;

import java.util.ArrayList;
import java.util.List;

/**
 * Parallel search uniques in list of items.
 * The peculiarity of impl is that if it's necessary to find uniques from a very large list, then one run will not be
 * enough, therefore impl has a <code>repeatCount</code> field that determines the number of runs of task.
 * If size of items after running parallel task is less than 10000,
 * {@link SearchList} implementation for finding uniques defined through constructor will
 * be used.
 * DEPRECATION. Implementation is not stable :(. For example, we have a list of numbers: 1, 2, 3, 1, 2, 3, 1, 2, 3
 * All elements in list is a duplicates: 1, 2, and 3, but if we set threadCount to 3, then each task get a sublist
 * 1, 2, 3: task1 - search uniques in 1, 2, 3; task2 - search uniques in 1, 2, 3; task3 - search uniques in
 * 1, 2, 3. Obviously, this return all items like a uniques, but it's not true.
 * @param <T> Search result and list of items to be searched for.
 *           Override {@link java.lang.Object#equals(T)} in custom classes for correct behaviour.
 *
 * @param <T> Search result and list of items to be searched for.
 *           Override {@link java.lang.Object#equals(T)} in custom classes for correct behaviour.
 */
@Deprecated
public final class SchUniquesParallel<T> implements SearchList<T> {

    private final Validator<Object> vdrNotNull;
    private final Validator<Integer> vdrNaturalInt;
    private final Validator<Integer> vdrPositiveInt;
    private final Validator<List<T>> vdrNotEmptyList;

    private final SearchList<T> sch;
    private final Integer threadCount;
    private final Integer repeatCount;

    /**
     * Ctor.
     * @param sch {@link SearchList} impl for finding uniques
     *            (default value {@link com.github.viise.poisk.sch.SchUniques}).
     * @param threadCount Thread count (default value 5).
     * @param repeatCount Repeat count of task (default value 5).
     */
    public SchUniquesParallel(SearchList<T> sch, Integer threadCount, Integer repeatCount) {
        this.sch = sch;
        this.threadCount = threadCount;
        this.repeatCount = repeatCount;

        this.vdrNotNull = new VdrNotNull();
        this.vdrNaturalInt = new VdrNaturalInt();
        this.vdrPositiveInt = new VdrPositiveInt();
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Ctor.
     * @param threadCount Thread count (default value 5).
     */
    public SchUniquesParallel(Integer threadCount) {
        this(threadCount, 5);
    }

    /**
     * Ctor.
     * @param threadCount Thread count (default value 5).
     * @param repeatCount Repeat count of task (default value 5).
     */
    public SchUniquesParallel(Integer threadCount, Integer repeatCount) {
        this(new SchUniques<>(), threadCount, repeatCount);
    }

    /**
     * Ctor.
     * @param sch Search impl for finding uniques
     *            (default value {@link com.github.viise.poisk.sch.SchUniques}).
     */
    public SchUniquesParallel(SearchList<T> sch) {
        this(sch, 5, 5);
    }

    /**
     * Ctor.
     */
    public SchUniquesParallel() {
        this(new SchUniques<>());
    }

    /**
     * Finding uniques in items.
     * @param items Items to find.
     * @return List of uniques.
     * @throws NotFoundException If sch is null, threadCount is not natural, repeatCount is negative, items is null or
     * empty, or uniques not found.
     */
    @Override
    public List<T> find(List<T> items) throws NotFoundException {
        try {
            vdrNotNull.validate("sch", sch);
            vdrNaturalInt.validate("threadCount", threadCount);
            vdrPositiveInt.validate("repeatCount", repeatCount);
            vdrNotEmptyList.validate("items", items);
        } catch (ValidationException e) {
            throw new NotFoundException(e);
        }

        SearchList<T> schListParallel = new SchListParallel<>(sch, threadCount);
        Search<T, List<T>> schFirstDup = new SchFirstDuplicate<>();
        List<T> result = new ArrayList<>();
        int currentRepeatCount = 0;
        do {
            if (!result.isEmpty() && result.size() < 10000) {
                return sch.find(result);
            } else {
                if (currentRepeatCount == 0) {
                    result = schListParallel.find(items);
                } else {
                    result = schListParallel.find(result);
                }
            }
            try {
                schFirstDup.find(result);
                currentRepeatCount++;
            } catch (NotFoundException ignored) {
                break;
            }
        } while (currentRepeatCount <= repeatCount);

        return result;
    }
}
